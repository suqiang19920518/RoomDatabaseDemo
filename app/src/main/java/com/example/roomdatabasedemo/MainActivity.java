package com.example.roomdatabasedemo;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roomdatabasedemo.bean.Address;
import com.example.roomdatabasedemo.bean.NameTuple;
import com.example.roomdatabasedemo.dao.BookDao;
import com.example.roomdatabasedemo.dao.UserDao;
import com.example.roomdatabasedemo.database.UserDatabase;
import com.example.roomdatabasedemo.entity.Book;
import com.example.roomdatabasedemo.entity.UserInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private UserDao userDao;
    private BookDao bookDao;
    private String idCardPrefix = "42102319940417478";
    private final String[] titles = new String[] {"西游记", "红楼梦", "三国演义", "水浒传"};
    private final String[] firstNames = new String[] {"wang", "zhang", "xu", "hu", "chen", "peng", "wu", "li"};
    private final String[] lastNames = new String[] {"gang", "fei", "weng", "xiong", "zheng", "jia", "yang", "qiang"};
    private List<UserInfo> userInfos = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_add_user).setOnClickListener(this);
        findViewById(R.id.btn_modify_user).setOnClickListener(this);
        findViewById(R.id.btn_query_user).setOnClickListener(this);
        findViewById(R.id.btn_delete_user).setOnClickListener(this);
        findViewById(R.id.btn_add_book).setOnClickListener(this);
        findViewById(R.id.btn_modify_book).setOnClickListener(this);
        findViewById(R.id.btn_query_book).setOnClickListener(this);
        findViewById(R.id.btn_delete_book).setOnClickListener(this);
        userDao = UserDatabase.getInstance(this).getUserDao();
        bookDao = UserDatabase.getInstance(this).getBookDao();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 8; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setAge(i + 15);
            userInfo.setIdCard(idCardPrefix + i);
            userInfo.setSex(i / 2 == 0 ? 0 : 1);
            userInfo.setFirstName(firstNames[i]);
            userInfo.setLastName(lastNames[i]);
            if (i == 4) {
                Address address = new Address();
                address.setCity("深圳");
                address.setState("宝安");
                address.setStreet("新安街道");
                address.setPostCode(10034);
                userInfo.setAddress(address);
                List<String> friends = new ArrayList<>();
                friends.add("张三");
                friends.add("李四");
                friends.add("王五");
                userInfo.setFriends(friends);
                Calendar calendar = Calendar.getInstance();
                calendar.set(1998, 4, 20);
                Date birthday = new Date(calendar.getTimeInMillis());
                userInfo.setBirthday(birthday);
            }
            userInfos.add(userInfo);
        }

        for (int i = 0; i < 4; i++) {
            Book book = new Book();
            book.setBookId(10001 + i);
            book.setTitle(titles[i]);
            book.setUserId(userInfos.get(2 + i).getIdCard());
            books.add(book);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_add_user) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    userDao.insert(userInfos.get(0));
                    userDao.insert(userInfos);
                    List<UserInfo> userInfos = userDao.findAll();
                    Log.e("userInfos", userInfos.toString());
                }
            }).start();

        } else if (id == R.id.btn_modify_user) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserInfo userInfo = userDao.findByName("yang");
                    if (userInfo != null) {
                        userInfo.setAge(29);
                        userDao.update(userInfo);
                    }
                    userDao.updateAge(idCardPrefix + 2, 55);
                    List<UserInfo> userInfos = userDao.findAll();
                    Log.e("userInfos", userInfos.toString());

                }
            }).start();

        } else if (id == R.id.btn_query_user) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<UserInfo> userInfos = userDao.findByAge(18, 22);
                    for (int i = 0; i < userInfos.size(); i++) {
                        UserInfo userInfo = userInfos.get(i);
                        userInfo.setAge(userInfo.getAge() + 10);
                    }
                    userDao.update(userInfos);
                    List<String> idCards = new ArrayList<>();
                    idCards.add(idCardPrefix + 2);
                    idCards.add(idCardPrefix + 3);
                    List<UserInfo> byIdCard = userDao.findByIdCard(idCards);
                    Log.e("userInfos", byIdCard.toString());
                    userInfos = userDao.findByAge(20);
                    Log.e("userInfos", userInfos.toString());
                    List<NameTuple> fullNames = userDao.findFullName(22);
                    Log.e("fullNames", fullNames.toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(1996, 2, 21);
                    Date from = new Date(calendar.getTimeInMillis());
                    calendar.set(1999, 3, 14);
                    Date to = new Date(calendar.getTimeInMillis());
                    UserInfo byBirth = userDao.findByBirth(from, to);
                    Log.e("byBirth", byBirth.toString());
                }
            }).start();

        } else if (id == R.id.btn_delete_user) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserInfo userInfo = userDao.findByPostCode(10034);
                    userDao.delete(userInfo);
                    List<UserInfo> userInfos = userDao.findAll();
                    Log.e("userInfos", userInfos.toString());
                    userDao.deleteAll();
                }
            }).start();

        } else if (id == R.id.btn_add_book) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bookDao.insert(books);
                    List<Book> books = bookDao.findAll();
                    Log.e("books", books.toString());
                }
            }).start();
        } else if (id == R.id.btn_modify_book) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Book book = bookDao.findByTitle(titles[2]);
                    if (book != null) {
                        book.setUserId(userInfos.get(6).getIdCard());
                        bookDao.update(book);
                    }
                    List<Book> books = bookDao.findAll();
                    Log.e("books", books.toString());
                }
            }).start();
        } else if (id == R.id.btn_query_book) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserInfo userInfo = userDao.findByName("xiong");
                    List<Book> books = bookDao.findByUserId(userInfo.getIdCard());
                    Log.e("books", books.toString());
//                    List<Book> book1 = bookDao.findByUserName("xiong");
//                    Log.e("book1", book1.toString());
                }
            }).start();
        } else if (id == R.id.btn_delete_book) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bookDao.deleteAll();
                    List<Book> books = bookDao.findAll();
                    Log.e("books", books.toString());
                }
            }).start();
        }
    }
}