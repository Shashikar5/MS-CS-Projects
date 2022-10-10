#include<iostream>
using namespace std;
int d=1;
struct bdnode
{
	int count=0;
	int key[2];
	bdnode* ptr[3];
};
typedef bdnode* BDPTR;
BDPTR T=NULL;
struct queue // Queue
{
	int size=7;
	int f=-1,r=-1;
	int a[20];
	
	void enqueue(int x)
	{
		if((r+1)%size==f)
		{
			cout<<"queue is full";
		}
		else
		{
			if(r==-1)
			{
				f=0;
			}
			
				r=(r+1)%size;
				a[r]=x;
			
		}
	}
    int dequeue()
    {
    	int t;
    	if(f==-1)
    	{
    		cout<<"empty";
		}
		else
		{
//			if(r==f)
//			{
//				r=-1;
//				f=-1;
//			}
			
		    t=a[f];
			f=(f+1)%size;
			return(t);
		}
	}
};
struct queue1 //FOR LEVEL ORDER
{
	int size=50;
	int f=-1,r=-1;
	BDPTR a[20];
	void enqueue(BDPTR x)
	{
		if((r+1)%size==f)
		{
			cout<<"queue is full";
		}
		else
		{
			if(r==-1)
			{
				f=0;
			}
			
				r=(r+1)%size;
				a[r]=x;
			
		}
	}
    BDPTR dequeue()
    {
    	BDPTR t;
    	if(f==-1)
    	{
    		cout<<"empty";
		}
	    t=a[f];
		if(r==f)
			{
				r=-1;
				f=-1;
			}
			else
			{
				f=(f+1)%size;
			}
			return t;
	}
};
void printLv(BDPTR &T) //Printing the tree as it is
{
	queue1 q;
	q.enqueue(T);
	q.enqueue(NULL);
	while(q.r!=-1)
	{
		BDPTR m=q.dequeue();
		if(m==NULL)
		{
			cout<<"\n";
			q.enqueue(NULL);
		}
	    else
	    {
	    for(int i=0;i<m->count;i++)
	    {
	    
	    		cout<<m->key[i]<<" ";
		
		}
	    for(int i=0;i<2*d+1;i++)
	    {
	    	if(m->ptr[i]!=NULL)
	    	{
	    		q.enqueue(m->ptr[i]);
			}
		}
		if(q.a[q.f]==NULL&&q.r==0)
	   {
	   	break;
	   }
	}
}
}
void sort(BDPTR &T,int x)
{
	int temp;
	if(T->key[0]!=0&&T->key[1]!=0)
	{
	
		if(T->key[0]>T->key[1])
		{
			temp=T->key[1];
			T->key[1]=T->key[0];
			T->key[0]=temp;
		}
		

	}
}
void overflow(BDPTR &T,int x,BDPTR &P,BDPTR &g)
{
	queue q;
	if(x<T->key[0])
	{
		q.enqueue(x);
		for(int j=0;j<2*d;j++)
		{
			q.enqueue(T->key[j]);
		}
	}
	else if(x>T->key[2*d-1])
	{
		for(int j=0;j<2*d;j++)
		{
			q.enqueue(T->key[j]);
		}
		q.enqueue(x);
	}//putting the elements in the sorted order in the queue
	else
	{
		for(int i=0;i<2*d;i++)
	{
		if(x>T->key[i]&&x<T->key[i+1])
		{
			for(int j=0;j<i+1;j++)
			{
				q.enqueue(T->key[j]);
			}
				q.enqueue(x);
			for(int j=i+1;j<2*d;j++)
			{
				q.enqueue(T->key[j]);
			}
		}
	}
	}
	//middle element= d+1
	T->count=0;
	for(int j=0;j<d;j++)
	{
		T->key[T->count++]=q.dequeue();
	}
		for(int j=d;j<2*d;j++)
		{
			T->key[j]=0;
		}
	x=q.dequeue();//middle element
	BDPTR m,n;
	BDPTR b;
	if(T==P) //overflow in the parent node
	{
		m=new(bdnode);
		m->key[m->count++]=x;
		
	    m->ptr[m->count-1]=T;
		
		b=new(bdnode);
	    for(int j=d;j<2*d;j++)
	    {
	    	b->key[b->count++]=q.dequeue();
	    }
		m->ptr[m->count]=b; //adjusting pointers
		
		if(T->ptr[T->count]!=NULL&&T->ptr[T->count+1]!=NULL) //in case of second overflow
		{
			b->ptr[b->count-1]=g;
			b->ptr[b->count]=T->ptr[T->count+1];
			T->ptr[T->count+1]=NULL;
		}
		else
		P=m;
	T=m;
	int static a=0;
	if(a==1)
	{
		printLv(T);
	}
	a++;
	}
	else if(P->ptr[P->count-1]==T) //common case overflow
	{
		
		n=new(bdnode);
		m=P->ptr[P->count];
	    for(int j=d;j<2*d;j++)
	    {
	    	n->key[n->count]=q.dequeue();
			n->count++;
		}
		if(P->count==2*d)
		{
			overflow(P,x,P,n); //overflow
		}
		else
	    {
	    P->key[P->count++]=x;
		sort(P,P->count);
		P->ptr[P->count]=m; 
		P->ptr[P->count-1]=n;
		P->ptr[P->count-2]=T;
		}
	}
}
void insert(BDPTR &T,int a[],int n)
{
	int static i=0;
	BDPTR P=NULL;
	BDPTR z=NULL;
 while(i<n)
 {
 	if(T==NULL)
	{
		T=new(bdnode);
		if(i==0)
		{
			while(i<2*d)
			{
				T->key[T->count]=a[i];
				i++;
				T->count++;
			}
			sort(T,T->count);
			for(int j=0;j<2*d+1;j++)
		{
		 T->ptr[j]=NULL;
		}
		P=T;
			if(T->count==2*d)
	    	{
	    		overflow(T,a[i],P,z); //overflow
	    		i++;
			}
		}
		else
	    {
        T->key[T->count]=a[i];
        i++;
		T->count++;
		}
	}
	else
	{
	
		if(a[i]<T->key[0])
	{
		
		if(T->ptr[0]!=NULL)
		{
			
			if(T->ptr[0]->count==2*d)
			{
				overflow(T->ptr[0],a[i++],P,z); //overflow 
				
			}
			else
			{
				T->ptr[0]->key[T->ptr[0]->count]=a[i];
				i++;
				T->ptr[0]->count++;
				sort(T->ptr[0],T->ptr[0]->count);
			}
		}
		else
		insert(T->ptr[0],a,n);
	}
	else	if(a[i]>T->key[2*d-1])
	{
		
		if(T->ptr[2*d]!=NULL)
		{
			if(T->ptr[2*d]->count==2*d)
			{
				overflow(T->ptr[2*d],a[i],P,z); //overflow
				i++;
			}
			else
			{
				T->ptr[2*d]->key[T->ptr[2*d]->count]=a[i];
				i++;
				T->ptr[2*d]->count++;
				sort(T,T->ptr[2*d]->count);
			}
		}
		else
		insert(T->ptr[2*d],a,n);
	}
	else
	{
		for(int j=0;j<2*d;j++)
	{
		if(a[i]>T->key[j]&&a[i]<T->key[j+1])
		{
		if(T->ptr[j+1]!=NULL)
		{
			if(T->ptr[j+1]->count==2*d)
			{
				overflow(T->ptr[j+1],a[i],P,z);//overflow
				i++;
			}
			else
			{
				T->ptr[j+1]->key[T->ptr[j+1]->count]=a[i];
				i++;
				T->ptr[j+1]->count++;
				sort(T->ptr[j+1],T->ptr[j+1]->count);
			}
		}
		else
		insert(T->ptr[i+1],a,n);
		}
	}
	
	}
	}
 }
 }
 int main()
 {
 	int n,temp;
 	T=NULL;
// 	cout<<"enter the number of elements\n";
// 	cin>>n;
// 	for(int i=0;i<n;i++)
// 	{
// 		cin>>a[i];
//	}
int a[]={3,7,9,1,6,4,8,2,5};
//int a[]={6,4,2,1,9};
n=9;
	insert(T,a,n);
//	printLv(T);
	//cout<<"\n";
//cout<<T->ptr[0]->key[0]<<" "<<T->ptr[1]->key[0]<<" "<<T->key[0];
//cout<<T->ptr[1]->key[1];
	return 0;
 }
