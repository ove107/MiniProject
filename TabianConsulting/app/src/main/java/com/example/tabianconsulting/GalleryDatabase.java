package com.example.tabianconsulting;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Category.class},version = 1,exportSchema = false)
public abstract class GalleryDatabase extends RoomDatabase {
    public abstract GalleryDao GalleryDao();

    private static GalleryDatabase databaseInstance;

    static GalleryDatabase getDatabase(final Context context) {
        if (databaseInstance == null) {
            synchronized (GalleryDatabase.class) {
                if (databaseInstance == null) {
                    databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            GalleryDatabase.class,
                            "Gallery_Database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sEmpCallback)
                            .build();
                }
            }
        }
        return databaseInstance;
    }

    private static RoomDatabase.Callback sEmpCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
      // new PopulateDbAsync(databaseInstance).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final GalleryDao mDao;
        String[] words = {"dolphin", "crocodile", "cobra","duck","buffalo","Lion","Panther"};
        PopulateDbAsync(GalleryDatabase db) {
            mDao = db.GalleryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            for (String name:words) {
                mDao.insert(new Category(name));

            }

            return null;
        }
    }

}
