package in.ashish29agre.greendao.example;

import in.ashish29agre.greendao.generator.example.db.Blog;
import in.ashish29agre.greendao.generator.example.db.BlogDao;
import in.ashish29agre.greendao.generator.example.db.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BlogListActivity extends ListActivity {

	private static final Logger log = Logger.getLogger(BlogListActivity.class
			.getName());
	List<Blog> blogs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.blog_list_activity);
		BlogDao blogDao = MainActivity.daoSession.getBlogDao();
		blogs = blogDao.loadAll();
		log.info("Blog List size" + blogs.size());
		ListAdapter adapter2 = new BlogArrayAdapter(this, blogs);
		setListAdapter(adapter2);
	}

	class BlogArrayAdapter extends ArrayAdapter<Blog> {
		private final Context context;
		private final List<Blog> values;

		public BlogArrayAdapter(Context context, List<Blog> values) {
			super(context, R.layout.blog_item, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.blog_item, parent, false);
			TextView title = (TextView) rowView.findViewById(R.id.b_title);
			TextView author = (TextView) rowView.findViewById(R.id.b_author);
			title.setText(values.get(position).getTitle());
			author.setText("By" + values.get(position).getUser().getName());
			return rowView;
		}

		@Override
		public Blog getItem(int position) {
			return values.get(position);
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Blog tempBlog = blogs.get(position);
		log.info("ListOfComments" + tempBlog.getCommentList().size());
		Intent seeBlogIntent = new Intent(this, ViewBlogActivity.class);
//		seeBlogIntent.putExtra("title", tempBlog.getTitle());
//		seeBlogIntent.putExtra("content", tempBlog.getText());
//		seeBlogIntent.putExtra("author", tempBlog.getUser().getName());
		seeBlogIntent.putExtra("blogId", tempBlog.getId());
		startActivity(seeBlogIntent);
	}
	
}
