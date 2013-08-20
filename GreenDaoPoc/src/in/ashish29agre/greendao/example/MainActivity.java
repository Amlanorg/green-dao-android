package in.ashish29agre.greendao.example;

import in.ashish29agre.greendao.generator.example.db.Blog;
import in.ashish29agre.greendao.generator.example.db.BlogDao;
import in.ashish29agre.greendao.generator.example.db.Comment;
import in.ashish29agre.greendao.generator.example.db.CommentDao;
import in.ashish29agre.greendao.generator.example.db.DaoMaster;
import in.ashish29agre.greendao.generator.example.db.DaoMaster.DevOpenHelper;
import in.ashish29agre.greendao.generator.example.db.DaoSession;
import in.ashish29agre.greendao.generator.example.db.User;
import in.ashish29agre.greendao.generator.example.db.UserDao;

import java.util.Date;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
	private static final Logger log = Logger.getLogger(MainActivity.class
			.getName());
	/*
	 * database fields
	 */
	private SQLiteDatabase db;

	protected static DaoMaster daoMaster;
	protected static DaoSession daoSession;
	private BlogDao blogDao;

	private Button addBlogButton;
	private Button viewBlogButton;
	private EditText blogTitleEdittext;
	private EditText blogDescriptionEdittext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		intiViews();
		intiDB();
	}

	@Override
	protected void onResume() {
		super.onResume();
		addBlogButton.setOnClickListener(this);
		viewBlogButton.setOnClickListener(this);
	}

	private void intiViews() {
		addBlogButton = (Button) findViewById(R.id.add_blog);
		viewBlogButton = (Button) findViewById(R.id.view_blog);
		blogTitleEdittext = (EditText) findViewById(R.id.blog_title);
		blogDescriptionEdittext = (EditText) findViewById(R.id.blog_text);
	}

	private void intiDB() {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "blogging_db",
				null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		blogDao = daoSession.getBlogDao();
		UserDao userDao = daoSession.getUserDao();
		User ashish = null;
		ashish = userDao.load(1l);
		if (ashish == null) {
			ashish = new User(null, " : Ashish R Agre");
			userDao.insert(ashish);
		}
		log.info("Author id" + ashish.getId());
		Comment comment = new Comment(null, "Hi Ashish doing good", 1l, null);
		comment.setBlog(blogDao.load(12121l));
		CommentDao commentDao = daoSession.getCommentDao();
		commentDao.insert(comment);
		log.info("Comment id" + comment.getId());
		log.info("Comment id" + comment.getBlogId());
	}

	private void addBlog() {
		String bTitle = blogTitleEdittext.getText().toString();
		String bText = blogDescriptionEdittext.getText().toString();
		blogTitleEdittext.setText("");
		blogDescriptionEdittext.setText("");

		// final DateFormat df =
		// DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
		// DateFormat.MEDIUM);
		// Note note = new Note(null, noteText, comment, new Date());
		Blog blog = new Blog(null, bTitle, bText, new Date(), 1);
		blogDao.insert(blog);
		Log.d("DaoExample", "Inserted new note, ID: " + blog.getId());

		// cursor.requery();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.add_blog:
			addBlog();
			break;
		case R.id.view_blog:
			Intent blogListIntent = new Intent(this, BlogListActivity.class);
			startActivity(blogListIntent);
			break;
		}
	}

}
