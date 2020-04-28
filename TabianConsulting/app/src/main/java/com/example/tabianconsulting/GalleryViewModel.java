package com.example.tabianconsulting;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GalleryViewModel extends AndroidViewModel {
private GalleryRepository mRepository;
private LiveData<List<Category>> mAllCategory;
    public GalleryViewModel(@NonNull Application application) {
        super(application);
        mRepository=new GalleryRepository(application);
        mAllCategory=mRepository.getmAllCategories();
    }
    LiveData<List<Category>> getmAllCategory(){ return mAllCategory; }
    public void insert(Category cat ){mRepository.insert(cat);}
    public void deleteCategory(Category cat){mRepository.deleteCategory(cat);}
    public void updateCategory(Category cat){mRepository.updateCategory(cat);}
}

//public class EmployeeViewModel extends AndroidViewModel {
//    private EmployeeRepository mRepository;
//    private LiveData<List<Employee>> mAllEmployee;
//
//
//    public EmployeeViewModel(Application application) {
//        super(application);
//        mRepository = new EmployeeRepository(application);
//        mAllEmployee = mRepository.getAllEmployee();
//    }
//    LiveData<List<Employee>> getAllEmployee(){
//        return mAllEmployee;
//    }
//
//
//    public void insert(Employee emp){mRepository.insert(emp);}
//}
