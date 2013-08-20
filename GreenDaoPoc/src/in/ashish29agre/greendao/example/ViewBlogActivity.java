package in.ashish29agre.greendao.example;

import in.ashish29agre.greendao.generator.example.db.Blog;
import in.ashish29agre.greendao.generator.example.db.Comment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ViewBlogActivity extends Activity {
	private TextView bTitleView;
	private TextView bContentView;
	private TextView bAuthorView;
	private ListView commentsListView;

	private String bTitle;
	private String bContent;
	private String bAuthor;

	private Blog blog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_blog_activity);
		// bTitle = getIntent().getStringExtra("title");
		// bContent = getIntent().getStringExtra("content");
		// bAuthor = getIntent().getStringExtra("author");
		blog = MainActivity.daoSession.getBlogDao().load(
				getIntent().getLongExtra("blogId", 1l));
		bTitle = blog.getTitle();
		bContent = blog.getText();
		bAuthor = blog.getUser().getName();
		bTitleView = (TextView) findViewById(R.id.view_blog_title);
		bContentView = (TextView) findViewById(R.id.view_blog_content);
		bAuthorView = (TextView) findViewById(R.id.view_blog_author);
		commentsListView = (ListView) findViewById(R.id.comments);
		ArrayAdapter<String> commentsAdapter = new ArrayAdapter<String>(this,
				R.layout.comment_layout, getComments());
		commentsListView.setAdapter(commentsAdapter);

	}

	@Override
	protected void onResume() {
		super.onResume();
		bTitleView.setText(bTitle);
		bContentView.setText(bContent);
		bAuthorView.setText("By : " + bAuthor);
	}

	private List<String> getComments() {
		List<String> comments = new ArrayList<String>();
		List<Comment> lComments = blog.getCommentList();
		for (Comment s : lComments) {
			comments.add(s.getComment() + " By " + s.getUser().getName());
		}
		return comments;
	}
}
