package com.example.tabianconsulting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    private LayoutInflater inflater;
    private List<Category> categories;
    private Context context;
    private GalleryViewModel viewModel;

    public interface MyClickListener{
        public void onMyItemclicked(View view,Category cat);
    }
private MyClickListener listener;
    public CategoryAdapter(MyClickListener listener,Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context=context;
        viewModel= ViewModelProviders.of((FragmentActivity) context).get(GalleryViewModel.class);
        this.listener=listener;
    }
    void setCategories(List<Category> cat){
        categories = cat;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_grid,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryAdapter.ViewHolder holder, final int position) {
        if(categories!=null){
            holder.category_name.setText((categories.get(position)).getName());
        }
        holder.m.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(final View v) {
                PopupMenu pop = new PopupMenu(context,holder.m);
                pop.inflate(R.menu.options);
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_edit:
                                listener.onMyItemclicked(v,categories.get(position));
                                break;
                            case R.id.menu_delete:
                                viewModel.deleteCategory(categories.get(position));
                                notifyItemRemoved(position);
                                //notifyDataSetChanged();
                                Toast.makeText(context,"Deleted",Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                pop.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if(categories!=null)
            return categories.size();
        else
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView category_name;
        TextView m;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            m=itemView.findViewById(R.id.digit);
           category_name= itemView.findViewById(R.id.category_name);
        }
    }
}
