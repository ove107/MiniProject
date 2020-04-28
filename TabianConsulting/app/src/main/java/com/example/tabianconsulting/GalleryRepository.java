package com.example.tabianconsulting;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GalleryRepository  {
    private GalleryDao mGallerydao;
    private LiveData<List<Category>> mAllCategories;

    GalleryRepository(Application application){
        GalleryDatabase db = GalleryDatabase.getDatabase(application);
        mGallerydao = db.GalleryDao();
        mAllCategories = mGallerydao.getAllCategory();
    }
LiveData<List<Category>> getmAllCategories(){
    return mAllCategories;
}
public void insert(Category cat){
        new insertAsyncTask(mGallerydao).execute(cat);
    }
private static class insertAsyncTask extends AsyncTask<Category,Void,Void>{

        private GalleryDao mAsyncTaskDao;

    public insertAsyncTask(GalleryDao mAsyncTaskDao) {
        this.mAsyncTaskDao = mAsyncTaskDao;
    }

    @Override
    protected Void doInBackground(Category... categories) {
        mAsyncTaskDao.insert(categories[0]);
        return null;
    }
}

    public void deleteCategory(Category cat){
        new deleteAsyncTask(mGallerydao).execute(cat);
    }
    private static class deleteAsyncTask extends AsyncTask<Category,Void,Void>{

        private GalleryDao mAsyncTaskDao;

        public deleteAsyncTask(GalleryDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            mAsyncTaskDao.deleteCategory(categories[0]);
            return null;
        }
    }


    public void updateCategory(Category cat){
        new updateAsyncTask(mGallerydao).execute(cat);
    }
    private static class updateAsyncTask extends AsyncTask<Category,Void,Void>{

        private GalleryDao mAsyncTaskDao;

        public updateAsyncTask(GalleryDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            mAsyncTaskDao.updateCategory(categories[0]);
            return null;
        }
    }

}




//public class EmployeeRepository {
//    private EmployeeDao mEmployeeDao;
//    private LiveData<List<Employee>> mAllEmployee;
//
//    EmployeeRepository(Application application){
//        EmployeeDatabase db = EmployeeDatabase.getDatabase(application);
//        mEmployeeDao = db.employeeDAO();
//        mAllEmployee = mEmployeeDao.getAllEmployee();
//    }
//    LiveData<List<Employee>> getAllEmployee(){return mAllEmployee;}
//
//    public void insert(Employee emp ){new insertAsyncTask(mEmployeeDao).execute(emp);}
//
//    private static class insertAsyncTask extends AsyncTask<Employee,Void,Void> {
//        private EmployeeDao mAsyncTaskDao;
//        insertAsyncTask(EmployeeDao emp){ mAsyncTaskDao = emp;}
//
//        @Override
//        protected Void doInBackground(final Employee... employees) {
//            mAsyncTaskDao.insert(employees[0]);
//            return null;
//        }
//    }
//}

