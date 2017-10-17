package mycontentprovider.example.com.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    static ArrayList<String[]> routes;
    static Context ctx;
    private int lastPosition = -1;
    private View.OnLongClickListener onLongClickListener;

    RVAdapter(ArrayList<String[]> routes, Context context){
        this.routes = routes;
        System.out.println("cheeeeeccc ");
        ctx=context;

    }

    public void add(String s[],int position) {
        position = position == lastPosition ? getItemCount()  : position;
        routes.add(s);
        System.out.println("yooooo "+s[1]+"  "+s[2]+" "+position);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if (position == lastPosition && getItemCount()>0)
            position = getItemCount() -1 ;

        if (position > lastPosition && position < getItemCount()) {
            routes.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("yahaaaaa");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        System.out.println("kkkkkkkkkkk");
        v.setBackgroundColor(Color.BLUE);
        System.out.println("aaaaaaaaaaaaaaaa");
        PersonViewHolder pvh = new PersonViewHolder(v);
       // pvh.itemView.setOnLongClickListener(RVAdapter.this);
        pvh.itemView.setTag(pvh);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {

        String str[]=routes.get(i);
       System.out.println("valuuu  "+i+"  "+str[0]+"  "+str[1]+"  "+str[2]+str[3]);
        personViewHolder.routeNo.setText(str[0]);
  //      personViewHolder.personAge.setText(str[2]);
        personViewHolder.src.setText(str[2]);
        personViewHolder.dest.setText(str[3]);
        System.out.println("ddddddddddddd");
        setAnimation(personViewHolder.container, i);
        //  personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);

    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(ctx, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public int getItemCount() {
        return routes.size();
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        onLongClickListener = onLongClickListener;
    }
    public View.OnLongClickListener getOnItemClickListener(){
        return onLongClickListener;
    }


    public interface OnLongClickListener {
        public void onLongClick(PersonViewHolder item, int position);

    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView routeNo,src,dest;
        LinearLayout container;
        private AdapterView.OnItemClickListener mItemClickListener;


        PersonViewHolder(final View itemView) {
            super(itemView);
            System.out.println("qqqqq");
            cv = (CardView) itemView.findViewById(R.id.cv);
            routeNo = (TextView) itemView.findViewById(R.id.rno);
//            personAge = (TextView) itemView.findViewById(R.id.person_age);
            //    personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            src = (TextView) itemView.findViewById(R.id.src);
            dest = (TextView) itemView.findViewById(R.id.dest);

            container = (LinearLayout) itemView.findViewById(R.id.ll);
            System.out.println("qqqqqqqqqqqqq");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //routeFrag.newInstance(routes.get(getPosition()));
                    Intent i = new Intent(ctx, routeInfo.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("details", routes.get(getPosition()));
                    ctx.startActivity(i);
                    System.out.println("finalllllllllllllll");
                }
            });

            }

    }

}
