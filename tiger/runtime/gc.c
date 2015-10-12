#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

// The Gimple Garbage Collector.
static void Tiger_gc();
static int round=0;
#define MILLION 1000000
//===============================================================//
// The Java Heap data structure.

/*   
      ----------------------------------------------------
      |                        |                         |
      ----------------------------------------------------
      ^\                      /^
      | \<~~~~~~~ size ~~~~~>/ |
    from                       to
 */
struct JavaHeap
{
  int size;         // in bytes, note that this if for semi-heap size
  char *from;       // the "from" space pointer
  char *fromFree;   // the next "free" space in the from space
  char *to;         // the "to" space pointer
  char *toStart;    // "start" address in the "to" space
  char *toNext;     // "next" free space pointer in the to space
};
char buffer[100];
// The Java heap, which is initialized by the following
// "heap_init" function.
struct JavaHeap heap;

// Lab 4, exercise 10:
// Given the heap size (in bytes), allocate a Java heap
// in the C heap, initialize the relevant fields.
void Tiger_heap_init (int heapSize)
{
  // You should write 7 statement here:
  // #1: allocate a chunk of memory of size "heapSize" using "malloc"
  char *p = (char *)malloc (heapSize); 
  // #2: initialize the "size" field, note that "size" field
  // is for semi-heap, but "heapSize" is for the whole heap.
  heap.size  = heapSize/2;
  char *q = memset(p,0,heapSize);
  // #3: initialize the "from" field (with what value?)
  heap.from = p;
  // #4: initialize the "fromFree" field (with what value?)
  heap.fromFree = p;
  // #5: initialize the "to" field (with what value?)
  heap.to = p +  heapSize/2 ;
  // #6: initizlize the "toStart" field with NULL;
  heap.toStart = NULL;
  // #7: initialize the "toNext" field with NULL;
  heap.toNext = NULL;
  return;
}

// The "prev" pointer, pointing to the top frame on the GC stack. 
// (see part A of Lab 4)
void *prev = 0;



//===============================================================//
// Object Model And allocation


// Lab 4: exercise 11:
// "new" a new object, do necessary initializations, and
// return the pointer (reference).
/*    ----------------
      | vptr      ---|----> (points to the virtual method table)
      |--------------|
      | isObjOrArray | (0: for normal objects)
      |--------------|
      | length       | (this field should be empty for normal objects)
      |--------------|
      | forwarding   | 
      |--------------|\
p---->| v_0          | \      
      |--------------|  s
      | ...          |  i
      |--------------|  z
      | v_{size-1}   | /e
      ----------------/
*/
// Try to allocate an object in the "from" space of the Java
// heap. Read Tiger book chapter 13.3 for details on the
// allocation.
// There are two cases to consider:
//   1. If the "from" space has enough space to hold this object, then
//      allocation succeeds, return the apropriate address (look at
//      the above figure, be careful);
//   2. if there is no enough space left in the "from" space, then
//      you should call the function "Tiger_gc()" to collect garbages.
//      and after the collection, there are still two sub-cases:
//        a: if there is enough space, you can do allocations just as case 1; 
//        b: if there is still no enough space, you can just issue
//           an error message ("OutOfMemory") and exit.
//           (However, a production compiler will try to expand
//           the Java heap.)
void *Tiger_new (void *vtable, int size)
{
  // You should write 4 statements for this function.
  // #1: "malloc" a chunk of memory of size "size": 
 	int *p;
	int free=heap.from+heap.size-heap.fromFree;
	if(size>free)
	{
		Tiger_gc();
		free=heap.from+heap.size-heap.fromFree;
		if(size>free)
		{
			printf("Out of memory..\n");
			if(flag == 1)
			{
				FILE *log=fopen("gcLog.txt","r");
				while(fgets(buffer , 100 , log))
					printf("%s\n",buffer);
				fclose(log);
			}
			exit(-1);
		}
	}
	memset(heap.fromFree,0,size);
	p=(int*)heap.fromFree;
	*p=(int)vtable;
	heap.fromFree+=size;
	return (void*)p;
  // #3: set up the "vtable" pointer properly:
}


// "new" an array of size "length", do necessary
// initializations. And each array comes with an
// extra "header" storing the array length and other information.
/*    ----------------
      | vptr         | (this field should be empty for an array)
      |--------------|
      | isObjOrArray | (1: for array)
      |--------------|
      | length       |
      |--------------|
      | forwarding   | 
      |--------------|\
p---->| e_0          | \      
      |--------------|  s
      | ...          |  i
      |--------------|  z
      | e_{length-1} | /e
      ----------------/
*/
// Try to allocate an array object in the "from" space of the Java
// heap. Read Tiger book chapter 13.3 for details on the
// allocation.
// There are two cases to consider:
//   1. If the "from" space has enough space to hold this array object, then
//      allocation succeeds, return the apropriate address (look at
//      the above figure, be careful);
//   2. if there is no enough space left in the "from" space, then
//      you should call the function "Tiger_gc()" to collect garbages.
//      and after the collection, there are still two sub-cases:
//        a: if there is enough space, you can do allocations just as case 1; 
//        b: if there is still no enough space, you can just issue
//           an error message ("OutOfMemory") and exit.
//           (However, a production compiler will try to expand
//           the Java heap.)
static int count=0;
void *Tiger_new_array (int length)
{
   	int *p;
	int free=heap.from+heap.size-heap.fromFree;
	int size=sizeof(int)*(4+length);
	if(size>free)
	{
		Tiger_gc();
		free=heap.from+heap.size-heap.fromFree;
		if(size>free)
		{
			printf("Out of memory..\n");
			if(flag == 1)
			{
				FILE *log=fopen("gcLog.txt","r");
				while(fgets(buffer , 100 , log))
					printf("%s\n",buffer);
				fclose(log);
			}
			exit(-1);
		}
	}
	memset(heap.fromFree,0,size);
	p=(int*)heap.fromFree;
	*(p+1)=1;
	*(p+2)=length;
	heap.fromFree+=size;
	return (void*)p;
}



void outputLog()
{
	flag = 1;
}

//===============================================================//
// The Gimple Garbage Collector

// Lab 4, exercise 12:
// A copying collector based-on Cheney's algorithm.
int Forward(void *p)
{
	int *forword , osize;
	char *class_gcmap;
	if(p>=(void*)heap.from&&p<(void*)heap.from+heap.size)
	{
		forword=(int*)p+3;
		if(*forword>=(int)heap.to&&*forword<(int)heap.to+heap.size)
			return *forword;
		else
		{
			osize=4;
			*forword=(int)heap.toNext;
			if(*((int*)p+1) == 0)
			{
				class_gcmap=(char*)(*(int*)(*(int*)p));
				osize+=strlen(class_gcmap);
				memcpy(heap.toNext,p,osize*sizeof(int));
			}
			else 
			{
				osize+=*((int*)p+2);
				memcpy(heap.toNext,p,osize*sizeof(int));
			}
			heap.toNext=heap.toNext+osize*sizeof(int);
		}
		return *forword;
	}
	return (int)p;
}
static void Tiger_gc ()
{
	struct timespec tpstart;
        struct timespec tpend;
        long timedif;
	char *head,*argu_gcmap,*locals_gcmap,*class_gcmap;
	int *add_argu,*add_locals,*forhead , obj,total;
	heap.toNext=heap.to;
	head=heap.toNext;
	total=0;
	printf("begain gc: %d\n" , round+1);
	void *oldp;
	oldp=prev;
	clock_gettime(CLOCK_MONOTONIC, &tpstart);
	for(;prev;prev=(void*)(*(int*)prev))
	{
		argu_gcmap=(char*)(*((int*)prev+1));
		add_argu=(int*)(*((int*)prev+2));
		locals_gcmap=(char*)(*((int*)prev+3));
		add_locals=(int*)prev+4;
		while(*argu_gcmap!='\0')
		{
			if('1'==*argu_gcmap)
				*add_argu=Forward((int*)(*add_argu));
			add_argu++;
			argu_gcmap++;
		}
		while(*locals_gcmap!='\0')
		{
			if('1'==*locals_gcmap)
			{
				*add_locals=Forward((int*)(*add_locals));
				add_locals++;
			}
			locals_gcmap++;
		}
		while(head<heap.toNext) //"bfs queue scan"
		{
			obj=4;
			if(*((int*)head+1) == 0)
			{
				forhead=(int*)head+4;
				class_gcmap=(char*)(*(int*)(*(int*)head));
				while(*class_gcmap!='\0')
				{
					if('1'==*class_gcmap)
						*forhead=Forward((int*)(*forhead));
					obj++;
					forhead++;
					class_gcmap++;
				}
			}
			else 
				obj+=*((int*)head+2);
			head=head+obj*sizeof(int);
			total+=obj;
		}
	}
	prev=oldp;
	int coll = heap.fromFree - heap.from -total * sizeof(int);
	char *tmp=heap.from;
	heap.from=heap.to;
	heap.to=tmp;
	heap.fromFree=heap.toNext;
	clock_gettime(CLOCK_MONOTONIC, &tpend);
        timedif = MILLION*(tpend.tv_sec-tpstart.tv_sec)+(tpend.tv_nsec-tpstart.tv_nsec)/1000;
	//printf("GC Round: %d ,it takes %lfs ,collected %d  bytes\n",++round,(double)timedif/1000, coll);
	if(round == 0)
	{
		FILE *log=fopen("gcLog.txt","w+");
		fclose(log);
	}
	sprintf(buffer,"GC Round: %d ,it takes %lfs ,collected %d  bytes\n",++round,(double)timedif/1000, coll);
	FILE *log=fopen("gcLog.txt","a+");
	int ret = fputs(buffer , log);
	fclose(log);
	printf("gc completed\n");

}

