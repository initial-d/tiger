// This is automatically generated by the Tiger compiler.
// Do NOT modify!

#include <memory.h>
// structures
struct TreeVisitor
{
  struct TreeVisitor_vtable *vptr;
  int isObjOrArray;
  unsigned int length;
  void * forwarding;
};
char TreeVisitor_gc_map[] = "";

struct TV
{
  struct TV_vtable *vptr;
  int isObjOrArray;
  unsigned int length;
  void * forwarding;
};
char TV_gc_map[] = "";

struct Tree
{
  struct Tree_vtable *vptr;
  int isObjOrArray;
  unsigned int length;
  void * forwarding;
  struct Tree * left;
  struct Tree * right;
  int key;
  int has_left;
  int has_right;
  struct Tree * my_null;
};
char Tree_gc_map[] = "110001";

struct Visitor
{
  struct Visitor_vtable *vptr;
  int isObjOrArray;
  unsigned int length;
  void * forwarding;
  struct Tree * l;
  struct Tree * r;
};
char Visitor_gc_map[] = "11";

struct MyVisitor
{
  struct MyVisitor_vtable *vptr;
  int isObjOrArray;
  unsigned int length;
  void * forwarding;
  struct Tree * l;
  struct Tree * r;
};
char MyVisitor_gc_map[] = "11";

// vtables structures
struct TreeVisitor_vtable
{
  char *TreeVisitorgc_map;
};


struct TreeVisitor_vtable TreeVisitor_vtable_;

struct TV_vtable
{
  char *TVgc_map;
  int (*Start)();
};


struct TV_vtable TV_vtable_;

struct Tree_vtable
{
  char *Treegc_map;
  int (*Init)();
  int (*SetRight)();
  int (*SetLeft)();
  struct Tree * (*GetRight)();
  struct Tree * (*GetLeft)();
  int (*GetKey)();
  int (*SetKey)();
  int (*GetHas_Right)();
  int (*GetHas_Left)();
  int (*SetHas_Left)();
  int (*SetHas_Right)();
  int (*Compare)();
  int (*Insert)();
  int (*Delete)();
  int (*Remove)();
  int (*RemoveRight)();
  int (*RemoveLeft)();
  int (*Search)();
  int (*Print)();
  int (*RecPrint)();
  int (*accept)();
};


struct Tree_vtable Tree_vtable_;

struct Visitor_vtable
{
  char *Visitorgc_map;
  int (*visit)();
};


struct Visitor_vtable Visitor_vtable_;

struct MyVisitor_vtable
{
  char *MyVisitorgc_map;
  int (*visit)();
};


struct MyVisitor_vtable MyVisitor_vtable_;


// gc_frames
extern void *prev;

struct TV_Start_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
  struct Tree * root;
  struct MyVisitor * v;
  struct Tree * x_1;
  struct Tree * x_2;
  struct Tree * x_3;
  struct Tree * x_4;
  struct Tree * x_5;
  struct Tree * x_6;
  struct Tree * x_7;
  struct Tree * x_8;
  struct Tree * x_9;
  struct Tree * x_10;
  struct Tree * x_11;
  struct Tree * x_12;
  struct Tree * x_13;
  struct Tree * x_14;
  struct Tree * x_15;
  struct Tree * x_16;
  struct Tree * x_17;
  struct Tree * x_18;
  struct Tree * x_19;
};
char TV_Start_arguments_gc_map[] = "1";
char TV_Start_locals_gc_map[] = "10011111111111111111111";

struct Tree_Init_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
};
char Tree_Init_arguments_gc_map[] = "10";
char Tree_Init_locals_gc_map[] = "";

struct Tree_SetRight_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
};
char Tree_SetRight_arguments_gc_map[] = "11";
char Tree_SetRight_locals_gc_map[] = "";

struct Tree_SetLeft_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
};
char Tree_SetLeft_arguments_gc_map[] = "11";
char Tree_SetLeft_locals_gc_map[] = "";

struct Tree_GetRight_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
};
char Tree_GetRight_arguments_gc_map[] = "1";
char Tree_GetRight_locals_gc_map[] = "";

struct Tree_GetLeft_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
};
char Tree_GetLeft_arguments_gc_map[] = "1";
char Tree_GetLeft_locals_gc_map[] = "";

struct Tree_GetKey_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
};
char Tree_GetKey_arguments_gc_map[] = "1";
char Tree_GetKey_locals_gc_map[] = "";

struct Tree_SetKey_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
};
char Tree_SetKey_arguments_gc_map[] = "10";
char Tree_SetKey_locals_gc_map[] = "";

struct Tree_GetHas_Right_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
};
char Tree_GetHas_Right_arguments_gc_map[] = "1";
char Tree_GetHas_Right_locals_gc_map[] = "";

struct Tree_GetHas_Left_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
};
char Tree_GetHas_Left_arguments_gc_map[] = "1";
char Tree_GetHas_Left_locals_gc_map[] = "";

struct Tree_SetHas_Left_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
};
char Tree_SetHas_Left_arguments_gc_map[] = "10";
char Tree_SetHas_Left_locals_gc_map[] = "";

struct Tree_SetHas_Right_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
};
char Tree_SetHas_Right_arguments_gc_map[] = "10";
char Tree_SetHas_Right_locals_gc_map[] = "";

struct Tree_Compare_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
};
char Tree_Compare_arguments_gc_map[] = "100";
char Tree_Compare_locals_gc_map[] = "00";

struct Tree_Insert_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
  struct Tree * new_node;
  struct Tree * current_node;
  struct Tree * x_20;
  struct Tree * x_21;
  struct Tree * x_22;
  struct Tree * x_23;
  struct Tree * x_24;
  struct Tree * x_25;
  struct Tree * x_26;
  struct Tree * x_27;
  struct Tree * x_28;
  struct Tree * x_29;
};
char Tree_Insert_arguments_gc_map[] = "10";
char Tree_Insert_locals_gc_map[] = "101001111111111";

struct Tree_Delete_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
  struct Tree * current_node;
  struct Tree * parent_node;
  struct Tree * x_30;
  struct Tree * x_31;
  struct Tree * x_32;
  struct Tree * x_33;
  struct Tree * x_34;
  struct Tree * x_35;
  struct Tree * x_36;
  struct Tree * x_37;
  struct Tree * x_38;
};
char Tree_Delete_arguments_gc_map[] = "10";
char Tree_Delete_locals_gc_map[] = "1100000111111111";

struct Tree_Remove_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
  struct Tree * x_39;
  struct Tree * x_40;
  struct Tree * x_41;
  struct Tree * x_42;
  struct Tree * x_43;
  struct Tree * x_44;
  struct Tree * x_45;
  struct Tree * x_46;
  struct Tree * x_47;
  struct Tree * x_48;
  struct Tree * x_49;
  struct Tree * x_50;
};
char Tree_Remove_arguments_gc_map[] = "111";
char Tree_Remove_locals_gc_map[] = "000111111111111";

struct Tree_RemoveRight_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
  struct Tree * x_51;
  struct Tree * x_52;
  struct Tree * x_53;
  struct Tree * x_54;
  struct Tree * x_55;
  struct Tree * x_56;
  struct Tree * x_57;
};
char Tree_RemoveRight_arguments_gc_map[] = "111";
char Tree_RemoveRight_locals_gc_map[] = "01111111";

struct Tree_RemoveLeft_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
  struct Tree * x_58;
  struct Tree * x_59;
  struct Tree * x_60;
  struct Tree * x_61;
  struct Tree * x_62;
  struct Tree * x_63;
  struct Tree * x_64;
};
char Tree_RemoveLeft_arguments_gc_map[] = "111";
char Tree_RemoveLeft_locals_gc_map[] = "01111111";

struct Tree_Search_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
  struct Tree * current_node;
  struct Tree * x_65;
  struct Tree * x_66;
  struct Tree * x_67;
  struct Tree * x_68;
  struct Tree * x_69;
};
char Tree_Search_arguments_gc_map[] = "10";
char Tree_Search_locals_gc_map[] = "100011111";

struct Tree_Print_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
  struct Tree * current_node;
  struct Tree * x_70;
};
char Tree_Print_arguments_gc_map[] = "1";
char Tree_Print_locals_gc_map[] = "011";

struct Tree_RecPrint_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
  struct Tree * x_71;
  struct Tree * x_72;
  struct Tree * x_73;
  struct Tree * x_74;
  struct Tree * x_75;
  struct Tree * x_76;
  struct Tree * x_77;
};
char Tree_RecPrint_arguments_gc_map[] = "11";
char Tree_RecPrint_locals_gc_map[] = "01111111";

struct Tree_accept_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
  struct Visitor * x_78;
};
char Tree_accept_arguments_gc_map[] = "11";
char Tree_accept_locals_gc_map[] = "01";

struct Visitor_visit_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
  struct Tree * x_79;
  struct Tree * x_80;
  struct Tree * x_81;
  struct Tree * x_82;
  struct Tree * x_83;
  struct Tree * x_84;
};
char Visitor_visit_arguments_gc_map[] = "11";
char Visitor_visit_locals_gc_map[] = "0111111";

struct MyVisitor_visit_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int  * arguments_base_address;
  char * locals_gc_map;
  struct Tree * x_85;
  struct Tree * x_86;
  struct Tree * x_87;
  struct Tree * x_88;
  struct Tree * x_89;
  struct Tree * x_90;
  struct Tree * x_91;
};
char MyVisitor_visit_arguments_gc_map[] = "11";
char MyVisitor_visit_locals_gc_map[] = "01111111";

// methods
int TV_Start(struct TV * this)
{
  struct TV_Start_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = TV_Start_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = TV_Start_locals_gc_map;
  int ntb;
  int nti;

  frame.root = ((struct Tree*)(Tiger_new (&Tree_vtable_, sizeof(struct Tree))));
  ntb = (frame.x_1=frame.root, frame.x_1->vptr->Init(frame.x_1, 16));
  ntb = (frame.x_2=frame.root, frame.x_2->vptr->Print(frame.x_2));
  System_out_println (100000000);
  ntb = (frame.x_3=frame.root, frame.x_3->vptr->Insert(frame.x_3, 8));
  ntb = (frame.x_4=frame.root, frame.x_4->vptr->Insert(frame.x_4, 24));
  ntb = (frame.x_5=frame.root, frame.x_5->vptr->Insert(frame.x_5, 4));
  ntb = (frame.x_6=frame.root, frame.x_6->vptr->Insert(frame.x_6, 12));
  ntb = (frame.x_7=frame.root, frame.x_7->vptr->Insert(frame.x_7, 20));
  ntb = (frame.x_8=frame.root, frame.x_8->vptr->Insert(frame.x_8, 28));
  ntb = (frame.x_9=frame.root, frame.x_9->vptr->Insert(frame.x_9, 14));
  ntb = (frame.x_10=frame.root, frame.x_10->vptr->Print(frame.x_10));
  System_out_println (100000000);
  frame.v = ((struct MyVisitor*)(Tiger_new (&MyVisitor_vtable_, sizeof(struct MyVisitor))));
  System_out_println (50000000);
  nti = (frame.x_11=frame.root, frame.x_11->vptr->accept(frame.x_11, frame.v));
  System_out_println (100000000);
  System_out_println ((frame.x_12=frame.root, frame.x_12->vptr->Search(frame.x_12, 24)));
  System_out_println ((frame.x_13=frame.root, frame.x_13->vptr->Search(frame.x_13, 12)));
  System_out_println ((frame.x_14=frame.root, frame.x_14->vptr->Search(frame.x_14, 16)));
  System_out_println ((frame.x_15=frame.root, frame.x_15->vptr->Search(frame.x_15, 50)));
  System_out_println ((frame.x_16=frame.root, frame.x_16->vptr->Search(frame.x_16, 12)));
  ntb = (frame.x_17=frame.root, frame.x_17->vptr->Delete(frame.x_17, 12));
  ntb = (frame.x_18=frame.root, frame.x_18->vptr->Print(frame.x_18));
  System_out_println ((frame.x_19=frame.root, frame.x_19->vptr->Search(frame.x_19, 12)));
  prev=frame.prev_gc_frame;
  return 0;
}
int Tree_Init(struct Tree * this, int v_key)
{
  struct Tree_Init_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_Init_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_Init_locals_gc_map;

  this->key = v_key;
  this->has_left = 0;
  this->has_right = 0;
  prev=frame.prev_gc_frame;
  return 1;
}
int Tree_SetRight(struct Tree * this, struct Tree * rn)
{
  struct Tree_SetRight_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_SetRight_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_SetRight_locals_gc_map;

  this->right = rn;
  prev=frame.prev_gc_frame;
  return 1;
}
int Tree_SetLeft(struct Tree * this, struct Tree * ln)
{
  struct Tree_SetLeft_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_SetLeft_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_SetLeft_locals_gc_map;

  this->left = ln;
  prev=frame.prev_gc_frame;
  return 1;
}
struct Tree * Tree_GetRight(struct Tree * this)
{
  struct Tree_GetRight_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_GetRight_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_GetRight_locals_gc_map;

  prev=frame.prev_gc_frame;
  return this->right;
}
struct Tree * Tree_GetLeft(struct Tree * this)
{
  struct Tree_GetLeft_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_GetLeft_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_GetLeft_locals_gc_map;

  prev=frame.prev_gc_frame;
  return this->left;
}
int Tree_GetKey(struct Tree * this)
{
  struct Tree_GetKey_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_GetKey_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_GetKey_locals_gc_map;

  prev=frame.prev_gc_frame;
  return this->key;
}
int Tree_SetKey(struct Tree * this, int v_key)
{
  struct Tree_SetKey_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_SetKey_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_SetKey_locals_gc_map;

  this->key = v_key;
  prev=frame.prev_gc_frame;
  return 1;
}
int Tree_GetHas_Right(struct Tree * this)
{
  struct Tree_GetHas_Right_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_GetHas_Right_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_GetHas_Right_locals_gc_map;

  prev=frame.prev_gc_frame;
  return this->has_right;
}
int Tree_GetHas_Left(struct Tree * this)
{
  struct Tree_GetHas_Left_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_GetHas_Left_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_GetHas_Left_locals_gc_map;

  prev=frame.prev_gc_frame;
  return this->has_left;
}
int Tree_SetHas_Left(struct Tree * this, int val)
{
  struct Tree_SetHas_Left_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_SetHas_Left_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_SetHas_Left_locals_gc_map;

  this->has_left = val;
  prev=frame.prev_gc_frame;
  return 1;
}
int Tree_SetHas_Right(struct Tree * this, int val)
{
  struct Tree_SetHas_Right_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_SetHas_Right_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_SetHas_Right_locals_gc_map;

  this->has_right = val;
  prev=frame.prev_gc_frame;
  return 1;
}
int Tree_Compare(struct Tree * this, int num1, int num2)
{
  struct Tree_Compare_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_Compare_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_Compare_locals_gc_map;
  int ntb;
  int nti;

  ntb = 0;
  nti = num2 + 1;
  if (num1 < num2)
    ntb = 0;

  else
    if (!(num1 < nti))
      ntb = 0;

    else
      ntb = 1;


  prev=frame.prev_gc_frame;
  return ntb;
}
int Tree_Insert(struct Tree * this, int v_key)
{
  struct Tree_Insert_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_Insert_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_Insert_locals_gc_map;
  int ntb;
  int cont;
  int key_aux;

  frame.new_node = ((struct Tree*)(Tiger_new (&Tree_vtable_, sizeof(struct Tree))));
  ntb = (frame.x_20=frame.new_node, frame.x_20->vptr->Init(frame.x_20, v_key));
  frame.current_node = this;
  cont = 1;
  while (cont)
    {
      key_aux = (frame.x_21=frame.current_node, frame.x_21->vptr->GetKey(frame.x_21));
      if (v_key < key_aux)
      {
        if ((frame.x_22=frame.current_node, frame.x_22->vptr->GetHas_Left(frame.x_22)))
        frame.current_node = (frame.x_23=frame.current_node, frame.x_23->vptr->GetLeft(frame.x_23));

      else
        {
          cont = 0;
          ntb = (frame.x_24=frame.current_node, frame.x_24->vptr->SetHas_Left(frame.x_24, 1));
          ntb = (frame.x_25=frame.current_node, frame.x_25->vptr->SetLeft(frame.x_25, frame.new_node));
        }

      }

    else
      {
        if ((frame.x_26=frame.current_node, frame.x_26->vptr->GetHas_Right(frame.x_26)))
        frame.current_node = (frame.x_27=frame.current_node, frame.x_27->vptr->GetRight(frame.x_27));

      else
        {
          cont = 0;
          ntb = (frame.x_28=frame.current_node, frame.x_28->vptr->SetHas_Right(frame.x_28, 1));
          ntb = (frame.x_29=frame.current_node, frame.x_29->vptr->SetRight(frame.x_29, frame.new_node));
        }

      }

    }
  prev=frame.prev_gc_frame;
  return 1;
}
int Tree_Delete(struct Tree * this, int v_key)
{
  struct Tree_Delete_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_Delete_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_Delete_locals_gc_map;
  int cont;
  int found;
  int ntb;
  int is_root;
  int key_aux;

  frame.current_node = this;
  frame.parent_node = this;
  cont = 1;
  found = 0;
  is_root = 1;
  while (cont)
    {
      key_aux = (frame.x_30=frame.current_node, frame.x_30->vptr->GetKey(frame.x_30));
      if (v_key < key_aux)
      if ((frame.x_31=frame.current_node, frame.x_31->vptr->GetHas_Left(frame.x_31)))
        {
          frame.parent_node = frame.current_node;
          frame.current_node = (frame.x_32=frame.current_node, frame.x_32->vptr->GetLeft(frame.x_32));
        }

      else
        cont = 0;


    else
      if (key_aux < v_key)
        if ((frame.x_33=frame.current_node, frame.x_33->vptr->GetHas_Right(frame.x_33)))
          {
            frame.parent_node = frame.current_node;
            frame.current_node = (frame.x_34=frame.current_node, frame.x_34->vptr->GetRight(frame.x_34));
          }

        else
          cont = 0;


      else
        {
          if (is_root)
          if (!(frame.x_35=frame.current_node, frame.x_35->vptr->GetHas_Right(frame.x_35)) && !(frame.x_36=frame.current_node, frame.x_36->vptr->GetHas_Left(frame.x_36)))
            ntb = 1;

          else
            ntb = (frame.x_37=this, frame.x_37->vptr->Remove(frame.x_37, frame.parent_node, frame.current_node));


        else
          ntb = (frame.x_38=this, frame.x_38->vptr->Remove(frame.x_38, frame.parent_node, frame.current_node));

          found = 1;
          cont = 0;
        }


      is_root = 0;
    }
  prev=frame.prev_gc_frame;
  return found;
}
int Tree_Remove(struct Tree * this, struct Tree * p_node, struct Tree * c_node)
{
  struct Tree_Remove_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_Remove_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_Remove_locals_gc_map;
  int ntb;
  int auxkey1;
  int auxkey2;

  if ((frame.x_39=c_node, frame.x_39->vptr->GetHas_Left(frame.x_39)))
    ntb = (frame.x_40=this, frame.x_40->vptr->RemoveLeft(frame.x_40, p_node, c_node));

  else
    if ((frame.x_41=c_node, frame.x_41->vptr->GetHas_Right(frame.x_41)))
      ntb = (frame.x_42=this, frame.x_42->vptr->RemoveRight(frame.x_42, p_node, c_node));

    else
      {
        auxkey1 = (frame.x_43=c_node, frame.x_43->vptr->GetKey(frame.x_43));
        auxkey2 = (frame.x_45=((frame.x_44=p_node, frame.x_44->vptr->GetLeft(frame.x_44))), frame.x_45->vptr->GetKey(frame.x_45));
        if ((frame.x_46=this, frame.x_46->vptr->Compare(frame.x_46, auxkey1, auxkey2)))
        {
          ntb = (frame.x_47=p_node, frame.x_47->vptr->SetLeft(frame.x_47, this->my_null));
          ntb = (frame.x_48=p_node, frame.x_48->vptr->SetHas_Left(frame.x_48, 0));
        }

      else
        {
          ntb = (frame.x_49=p_node, frame.x_49->vptr->SetRight(frame.x_49, this->my_null));
          ntb = (frame.x_50=p_node, frame.x_50->vptr->SetHas_Right(frame.x_50, 0));
        }

      }


  prev=frame.prev_gc_frame;
  return 1;
}
int Tree_RemoveRight(struct Tree * this, struct Tree * p_node, struct Tree * c_node)
{
  struct Tree_RemoveRight_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_RemoveRight_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_RemoveRight_locals_gc_map;
  int ntb;

  while ((frame.x_51=c_node, frame.x_51->vptr->GetHas_Right(frame.x_51)))
    {
      ntb = (frame.x_52=c_node, frame.x_52->vptr->SetKey(frame.x_52, (frame.x_54=((frame.x_53=c_node, frame.x_53->vptr->GetRight(frame.x_53))), frame.x_54->vptr->GetKey(frame.x_54))));
      p_node = c_node;
      c_node = (frame.x_55=c_node, frame.x_55->vptr->GetRight(frame.x_55));
    }
  ntb = (frame.x_56=p_node, frame.x_56->vptr->SetRight(frame.x_56, this->my_null));
  ntb = (frame.x_57=p_node, frame.x_57->vptr->SetHas_Right(frame.x_57, 0));
  prev=frame.prev_gc_frame;
  return 1;
}
int Tree_RemoveLeft(struct Tree * this, struct Tree * p_node, struct Tree * c_node)
{
  struct Tree_RemoveLeft_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_RemoveLeft_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_RemoveLeft_locals_gc_map;
  int ntb;

  while ((frame.x_58=c_node, frame.x_58->vptr->GetHas_Left(frame.x_58)))
    {
      ntb = (frame.x_59=c_node, frame.x_59->vptr->SetKey(frame.x_59, (frame.x_61=((frame.x_60=c_node, frame.x_60->vptr->GetLeft(frame.x_60))), frame.x_61->vptr->GetKey(frame.x_61))));
      p_node = c_node;
      c_node = (frame.x_62=c_node, frame.x_62->vptr->GetLeft(frame.x_62));
    }
  ntb = (frame.x_63=p_node, frame.x_63->vptr->SetLeft(frame.x_63, this->my_null));
  ntb = (frame.x_64=p_node, frame.x_64->vptr->SetHas_Left(frame.x_64, 0));
  prev=frame.prev_gc_frame;
  return 1;
}
int Tree_Search(struct Tree * this, int v_key)
{
  struct Tree_Search_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_Search_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_Search_locals_gc_map;
  int ifound;
  int cont;
  int key_aux;

  frame.current_node = this;
  cont = 1;
  ifound = 0;
  while (cont)
    {
      key_aux = (frame.x_65=frame.current_node, frame.x_65->vptr->GetKey(frame.x_65));
      if (v_key < key_aux)
      if ((frame.x_66=frame.current_node, frame.x_66->vptr->GetHas_Left(frame.x_66)))
        frame.current_node = (frame.x_67=frame.current_node, frame.x_67->vptr->GetLeft(frame.x_67));

      else
        cont = 0;


    else
      if (key_aux < v_key)
        if ((frame.x_68=frame.current_node, frame.x_68->vptr->GetHas_Right(frame.x_68)))
          frame.current_node = (frame.x_69=frame.current_node, frame.x_69->vptr->GetRight(frame.x_69));

        else
          cont = 0;


      else
        {
          ifound = 1;
          cont = 0;
        }


    }
  prev=frame.prev_gc_frame;
  return ifound;
}
int Tree_Print(struct Tree * this)
{
  struct Tree_Print_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_Print_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_Print_locals_gc_map;
  int ntb;

  frame.current_node = this;
  ntb = (frame.x_70=this, frame.x_70->vptr->RecPrint(frame.x_70, frame.current_node));
  prev=frame.prev_gc_frame;
  return 1;
}
int Tree_RecPrint(struct Tree * this, struct Tree * node)
{
  struct Tree_RecPrint_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_RecPrint_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_RecPrint_locals_gc_map;
  int ntb;

  if ((frame.x_71=node, frame.x_71->vptr->GetHas_Left(frame.x_71)))
    {
      ntb = (frame.x_72=this, frame.x_72->vptr->RecPrint(frame.x_72, (frame.x_73=node, frame.x_73->vptr->GetLeft(frame.x_73))));
    }

  else
    ntb = 1;

  System_out_println ((frame.x_74=node, frame.x_74->vptr->GetKey(frame.x_74)));
  if ((frame.x_75=node, frame.x_75->vptr->GetHas_Right(frame.x_75)))
    {
      ntb = (frame.x_76=this, frame.x_76->vptr->RecPrint(frame.x_76, (frame.x_77=node, frame.x_77->vptr->GetRight(frame.x_77))));
    }

  else
    ntb = 1;

  prev=frame.prev_gc_frame;
  return 1;
}
int Tree_accept(struct Tree * this, struct Visitor * v)
{
  struct Tree_accept_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tree_accept_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Tree_accept_locals_gc_map;
  int nti;

  System_out_println (333);
  nti = (frame.x_78=v, frame.x_78->vptr->visit(frame.x_78, this));
  prev=frame.prev_gc_frame;
  return 0;
}
int Visitor_visit(struct Visitor * this, struct Tree * n)
{
  struct Visitor_visit_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Visitor_visit_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = Visitor_visit_locals_gc_map;
  int nti;

  if ((frame.x_79=n, frame.x_79->vptr->GetHas_Right(frame.x_79)))
    {
      this->r = (frame.x_80=n, frame.x_80->vptr->GetRight(frame.x_80));
      nti = (frame.x_81=this->r, frame.x_81->vptr->accept(frame.x_81, this));
    }

  else
    nti = 0;

  if ((frame.x_82=n, frame.x_82->vptr->GetHas_Left(frame.x_82)))
    {
      this->l = (frame.x_83=n, frame.x_83->vptr->GetLeft(frame.x_83));
      nti = (frame.x_84=this->l, frame.x_84->vptr->accept(frame.x_84, this));
    }

  else
    nti = 0;

  prev=frame.prev_gc_frame;
  return 0;
}
int MyVisitor_visit(struct MyVisitor * this, struct Tree * n)
{
  struct MyVisitor_visit_gc_frame frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = MyVisitor_visit_arguments_gc_map;
  frame.arguments_base_address = (int*)&this;
  frame.locals_gc_map = MyVisitor_visit_locals_gc_map;
  int nti;

  if ((frame.x_85=n, frame.x_85->vptr->GetHas_Right(frame.x_85)))
    {
      this->r = (frame.x_86=n, frame.x_86->vptr->GetRight(frame.x_86));
      nti = (frame.x_87=this->r, frame.x_87->vptr->accept(frame.x_87, this));
    }

  else
    nti = 0;

  System_out_println ((frame.x_88=n, frame.x_88->vptr->GetKey(frame.x_88)));
  if ((frame.x_89=n, frame.x_89->vptr->GetHas_Left(frame.x_89)))
    {
      this->l = (frame.x_90=n, frame.x_90->vptr->GetLeft(frame.x_90));
      nti = (frame.x_91=this->l, frame.x_91->vptr->accept(frame.x_91, this));
    }

  else
    nti = 0;

  prev=frame.prev_gc_frame;
  return 0;
}

// vtables
struct TreeVisitor_vtable TreeVisitor_vtable_ = 
{
  TreeVisitor_gc_map,
};

struct TV_vtable TV_vtable_ = 
{
  TV_gc_map,
  TV_Start,
};

struct Tree_vtable Tree_vtable_ = 
{
  Tree_gc_map,
  Tree_Init,
  Tree_SetRight,
  Tree_SetLeft,
  Tree_GetRight,
  Tree_GetLeft,
  Tree_GetKey,
  Tree_SetKey,
  Tree_GetHas_Right,
  Tree_GetHas_Left,
  Tree_SetHas_Left,
  Tree_SetHas_Right,
  Tree_Compare,
  Tree_Insert,
  Tree_Delete,
  Tree_Remove,
  Tree_RemoveRight,
  Tree_RemoveLeft,
  Tree_Search,
  Tree_Print,
  Tree_RecPrint,
  Tree_accept,
};

struct Visitor_vtable Visitor_vtable_ = 
{
  Visitor_gc_map,
  Visitor_visit,
};

struct MyVisitor_vtable MyVisitor_vtable_ = 
{
  MyVisitor_gc_map,
  MyVisitor_visit,
};


//main_method_gc_frame
struct Tiger_main_gc_frame
{
  void * prev_gc_frame;
  char * arguments_gc_map;
  int * arguments_base_address;
  char * locals_gc_map;
  struct TV * x_0;
};
char  Tiger_main_arguments_gc_map[] = "";
char  Tiger_main_locals_gc_map[] = "1";

// main method
int Tiger_main ()
{
  struct Tiger_main_gc_frame  frame;
  memset(&frame,0,sizeof(frame));
  frame.prev_gc_frame = prev;
  prev = &frame;
  frame.arguments_gc_map = Tiger_main_arguments_gc_map;
  frame.locals_gc_map = Tiger_main_locals_gc_map;

  System_out_println ((frame.x_0=((struct TV*)(Tiger_new (&TV_vtable_, sizeof(struct TV)))), frame.x_0->vptr->Start(frame.x_0)));
  prev=frame.prev_gc_frame;
}



