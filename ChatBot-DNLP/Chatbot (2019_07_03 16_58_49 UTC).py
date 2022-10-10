#building a chatbot with deep NLP
# control + enter to run
#importing libaries
import numpy as np
import tensorflow as tf
import re
import time
#split(k) means that k in the sentences is removed
#replace("k","") means k is replaced by nothing


                      ###PART-1 DATA PREPROCESSNG###

#importing dataset
lines=open('movie_lines.txt',encoding = 'utf8',errors = 'ignore').read().split('\n')
conversions=open('movie_conversations.txt',encoding = 'utf8',errors = 'ignore').read().split('\n')

#creating a dict which maps each line and its ID for lines
id2line = dict()
for line in lines:
    _line=line.split(' +++$+++ ') #_line means local variable
    if(len(_line)==5):
        id2line[_line[0]]=_line[4]
        
#creating a list for the conversions
conversion_ids = []
for conversion in conversions[:-1]: #for loop for the all expect the last element [:-1]
    _conversion=conversion.split(' +++$+++ ')[-1][1:-1].replace("'","").replace(" ","") #[-1][1:-1] means for last element first and last pos are not included 
    conversion_ids.append(_conversion.split(","))
    
#creating a list for questions and answers
questions = []
answers = []
for conversion in conversion_ids:
    for i in range(0,len(conversion)-1,2):
        questions.append(id2line[conversion[i]])
        answers.append(id2line[conversion[i+1]])
        
#doing the first cleaning of the texts
def clean_text(text):
    text = text.lower()
    text = re.sub(r"i'm", "i am", text)
    text = re.sub(r"he's", "he is", text)
    text = re.sub(r"she's", "she is", text)
    text = re.sub(r"that's", "that is", text)
    text = re.sub(r"what's", "what is", text)
    text = re.sub(r"where's", "where is", text)
    text = re.sub(r"how's", "how is", text)
    text = re.sub(r"\'ll", " will", text)
    text = re.sub(r"\'ve", " have", text)
    text = re.sub(r"\'re", " are", text)
    text = re.sub(r"\'d", " would", text)
    text = re.sub(r"n't", " not", text)
    text = re.sub(r"won't", "will not", text)
    text = re.sub(r"can't", "cannot", text)
    text = re.sub(r"[-()\"#/@;:<>{}`+=~|.!?,]", "", text)
    return text

Questions=[] #clean answers and questions
for i in questions:
    Questions.append(clean_text(i))
    
Answers=[]
for j in answers:
    Answers.append(clean_text(j))
    
#number of occurences of each word
word2count = dict()
for i in Answers:
    for j in i.split():
        if(j not in word2count):
            word2count[j]=1
        else:
            word2count[j]+=1

for i in Questions:
    for j in i.split():
        if(j not in word2count):
            word2count[j]=1
        else:
            word2count[j]+=1
#creating two dict that will map the question word and the answer word to a unique integer
answerwordToint = dict()
questionwordToint = dict()
threshold = 20
word_count=0
for i,count in word2count.items():
    if(count>=threshold):
        answerwordToint[i]=word_count
        word_count+=1
word_count=0
for i,count in word2count.items():
    if(count>=threshold):
        questionwordToint[i]=word_count
        word_count+=1 
#adding tokens to the 2 dict
tokens = ['<PAD>','<EOS>','<OUT>','<SOS>']
for i in tokens:
    answerwordToint[i]=len(answerwordToint)+1
for j in tokens:
    questionwordToint[j]=len(questionwordToint)+1
#inverse mapping of answerswordtoint dict
answerintToword = {w_i:w for w,w_i in answerwordToint.items()}
 #answerintToword = dict()
 #for i in answerwordToint:
  #   answerintToword[answerwordToint[i]] = i
#adding EOS token to the clean_answers
for i in range(len(Answers)):
    Answers[i]+=' <EOS>'
#converting the clean_answers and clean_questions to int from the dict and replacing the 
#filtered word by <OUT>
answers_int = []
for i in Answers:
    ints = []
    for j in i.split():
        if(j not in answerwordToint):
            ints.append(answerwordToint['<OUT>'])
        else:
            ints.append(answerwordToint[j])
        answers_int.append(ints)
questions_int = []
for i in Questions:
    ints = []
    for j in i.split():
        if(j not in questionwordToint):
            ints.append(questionwordToint['<OUT>'])
        else:
            ints.append(questionwordToint[j])
        questions_int.append(ints)
#sorting the answers and questions by length of the questions
sorted_clean_questions = []
sorted_clean_answers = []
for length in range(1,25+1):
    for i in enumerate(questions_int):
        if(len(i[1])==length):
            sorted_clean_questions.append(questions_int[i[0]])
            sorted_clean_answers.append(answers_int[i[0]])
                   ###PART-2 BUILDING A SEQ2SEQ MODEL###
#Creating placeholders for tensorflow
def model_inp():
    inputs=tf.placeholder(tf.int32,[None,None],name='input')
    targets=tf.placeholder(tf.int32,[None,None],name='targets')
    lr=tf.placeholder(tf.float32,[None,None],name='learning_rate')
    keep_prob=tf.placeholder(tf.float32,[None,None],name='book_prob')
    return inputs,targets,lr,keep_prob
#PREPROSSING THE TARGETS
def preprocess_targets(targets,answerwordToint,batch_size):
    left_side=tf.fill([batch_size,1],answerwordToint['<SOS>'])
    right_side=tf.strided_slice(targets,[0,0],[batch_size,-1],[1,1])
    preprossed_target = tf.concat([left_side,right_side],1)
    return preprossed_target
# Creating the Encoder RNN
def encoder_rnn(rnn_inputs, rnn_size, num_layers, keep_prob, sequence_length):
    lstm = tf.contrib.rnn.BasicLSTMCell(rnn_size)
    lstm_dropout = tf.contrib.rnn.DropoutWrapper(lstm, input_keep_prob = keep_prob)
    encoder_cell = tf.contrib.rnn.MultiRNNCell([lstm_dropout] * num_layers)
    encoder_output, encoder_state = tf.nn.bidirectional_dynamic_rnn(cell_fw = encoder_cell,
                                                                    cell_bw = encoder_cell,
                                                                    sequence_length = sequence_length,
                                                                    inputs = rnn_inputs,
                                                                    dtype = tf.float32)
    return encoder_state
# Decoding the training set
def decode_training_set(encoder_state, decoder_cell, decoder_embedded_input, sequence_length, decoding_scope, output_function, keep_prob, batch_size):
    attention_states = tf.zeros([batch_size, 1, decoder_cell.output_size])
    attention_keys, attention_values, attention_score_function, attention_construct_function = tf.contrib.seq2seq.prepare_attention(attention_states, attention_option = "bahdanau", num_units = decoder_cell.output_size)
    training_decoder_function = tf.contrib.seq2seq.attention_decoder_fn_train(encoder_state[0],
                                                                              attention_keys,
                                                                              attention_values,
                                                                              attention_score_function,
                                                                              attention_construct_function,
                                                                              name = "attn_dec_train")
    decoder_output, decoder_final_state, decoder_final_context_state = tf.contrib.seq2seq.dynamic_rnn_decoder(decoder_cell,
                                                                                                              training_decoder_function,
                                                                                                              decoder_embedded_input,
                                                                                                              sequence_length,
                                                                                                              scope = decoding_scope)
    decoder_output_dropout = tf.nn.dropout(decoder_output, keep_prob)
    return output_function(decoder_output_dropout)
 
# Decoding the test/validation set
def decode_test_set(encoder_state, decoder_cell, decoder_embeddings_matrix, sos_id, eos_id, maximum_length, num_words, decoding_scope, output_function, keep_prob, batch_size):
    attention_states = tf.zeros([batch_size, 1, decoder_cell.output_size])
    attention_keys, attention_values, attention_score_function, attention_construct_function = tf.contrib.seq2seq.prepare_attention(attention_states, attention_option = "bahdanau", num_units = decoder_cell.output_size)
    test_decoder_function = tf.contrib.seq2seq.attention_decoder_fn_inference(output_function,
                                                                              encoder_state[0],
                                                                              attention_keys,
                                                                              attention_values,
                                                                              attention_score_function,
                                                                              attention_construct_function,
                                                                              decoder_embeddings_matrix,
                                                                              sos_id,
                                                                              eos_id,
                                                                              maximum_length,
                                                                              num_words,
                                                                              name = "attn_dec_inf")
    test_predictions, decoder_final_state, decoder_final_context_state = tf.contrib.seq2seq.dynamic_rnn_decoder(decoder_cell,
                                                                                                                test_decoder_function,
                                                                                                                scope = decoding_scope)
    return test_predictions
 
# Creating the Decoder RNN
def decoder_rnn(decoder_embedded_input, decoder_embeddings_matrix, encoder_state, num_words, sequence_length, rnn_size, num_layers, word2int, keep_prob, batch_size):
    with tf.variable_scope("decoding") as decoding_scope:
        lstm = tf.contrib.rnn.BasicLSTMCell(rnn_size)
        lstm_dropout = tf.contrib.rnn.DropoutWrapper(lstm, input_keep_prob = keep_prob)
        decoder_cell = tf.contrib.rnn.MultiRNNCell([lstm_dropout] * num_layers)
        weights = tf.truncated_normal_initializer(stddev = 0.1)
        biases = tf.zeros_initializer()
        output_function = lambda x: tf.contrib.layers.fully_connected(x,
                                                                      num_words,
                                                                      None,
                                                                      scope = decoding_scope,
                                                                      weights_initializer = weights,
                                                                      biases_initializer = biases)
        training_predictions = decode_training_set(encoder_state,
                                                   decoder_cell,
                                                   decoder_embedded_input,
                                                   sequence_length,
                                                   decoding_scope,
                                                   output_function,
                                                   keep_prob,
                                                   batch_size)
        decoding_scope.reuse_variables()
        test_predictions = decode_test_set(encoder_state,
                                           decoder_cell,
                                           decoder_embeddings_matrix,
                                           answerwordToint['<SOS>'],
                                           answerwordToint['<EOS>'],
                                           sequence_length - 1,
                                           num_words,
                                           decoding_scope,
                                           output_function,
                                           keep_prob,
                                           batch_size)
    return training_predictions, test_predictions
 
# Building the seq2seq model
def seq2seq_model(inputs, targets, keep_prob, batch_size, sequence_length, answers_num_words, questions_num_words, encoder_embedding_size, decoder_embedding_size, rnn_size, num_layers, questionwordToint):
    encoder_embedded_input = tf.contrib.layers.embed_sequence(inputs,
                                                              answers_num_words + 1,
                                                              encoder_embedding_size,
                                                              initializer = tf.random_uniform_initializer(0, 1))
    encoder_state = encoder_rnn(encoder_embedded_input, rnn_size, num_layers, keep_prob, sequence_length)
    preprocessed_targets = preprocess_targets(targets, questionwordToint, batch_size)
    decoder_embeddings_matrix = tf.Variable(tf.random_uniform([questions_num_words + 1, decoder_embedding_size], 0, 1))
    decoder_embedded_input = tf.nn.embedding_lookup(decoder_embeddings_matrix, preprocessed_targets)
    training_predictions, test_predictions = decoder_rnn(decoder_embedded_input,
                                                         decoder_embeddings_matrix,
                                                         encoder_state,
                                                         questions_num_words,
                                                         sequence_length,
                                                         rnn_size,
                                                         num_layers,
                                                         questionwordToint,
                                                         keep_prob,
                                                         batch_size)
    return training_predictions, test_predictions
                      ###PART-3 TRAINING THE SEQ2SEQ MODEL###
 
# Setting the Hyperparameters
epochs = 100
batch_size = 32
rnn_size = 1024
num_layers = 3
encoding_embedding_size = 1024
decoding_embedding_size = 1024
learning_rate = 0.001
learning_rate_decay = 0.9
min_learning_rate = 0.0001
keep_probability = 0.5 #KEEP_PROB=1-DROP_OUT_RATE

#defining a session
tf.reset_default_graph()
session = tf.InteractiveSession()

#loading the model parameters
inputs, targets, lr, keep_prob = model_inp()

#setting the sequence length
sequence_length=tf.placeholder_with_default(25,None,name='sequence_length')

# Getting the shape of the inputs tensor
input_shape = tf.shape(inputs)

# Getting the training and test predictions
training_predictions, test_predictions = seq2seq_model(tf.reverse(inputs,[-1]),
                                                       targets,
                                                       keep_prob,
                                                       batch_size,
                                                       sequence_length,
                                                       len(answerwordToint),
                                                       len(questionwordToint),
                                                       encoding_embedding_size,
                                                       decoding_embedding_size,
                                                       rnn_size,
                                                       num_layers,
                                                       questionwordToint)