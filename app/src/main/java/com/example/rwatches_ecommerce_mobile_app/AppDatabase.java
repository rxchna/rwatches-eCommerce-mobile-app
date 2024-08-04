package com.example.rwatches_ecommerce_mobile_app;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities={UserModel.class, ProductModel.class, CartModel.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "app_db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            // Instantiate a builder
            Builder<AppDatabase> builder = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME);
            // Configure the builder - [TODO: use a seperate thread]
            builder.allowMainThreadQueries();
            // .build the DB instance class that will allow access the DB file
            instance = builder.build();
        }

//        if (instance == null) {
//            instance = Room.databaseBuilder(context.getApplicationContext(),
//                            AppDatabase.class, DB_NAME)
//                    .fallbackToDestructiveMigration() // Optional: handle database version changes
//                    .build();
//        }

        return instance;
    }

    public abstract UserDao userDao();
    public abstract ProductDao productDao();
    public abstract CartDao cartDao();

}
