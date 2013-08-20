package in.ashish29agre.greendao.generator.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {

	public static void main(String[] args) throws Exception {
		Schema schema = new Schema(1,
				"in.ashish29agre.greendao.generator.example.db");

		addSchema(schema);

		new DaoGenerator().generateAll(schema, "../GreenDaoPoc/src-gen");

	}

	private static void addSchema(Schema schema) {
		// Blog Entity
		Entity blog = schema.addEntity("Blog");
		blog.addIdProperty();
		blog.addStringProperty("title").notNull();
		blog.addStringProperty("text").notNull();
		blog.addDateProperty("date");
		// Blog author relation
		Entity user = schema.addEntity("User");
		user.addIdProperty();
		user.addStringProperty("name").notNull();

		Property userId = blog.addLongProperty("userId").notNull()
				.getProperty();
		blog.addToOne(user, userId);

		// Comment entity
		//
		Entity comment = schema.addEntity("Comment");
		comment.addIdProperty();
		comment.addStringProperty("comment");

		Property cUserId = comment.addLongProperty("userId").getProperty();
		Property blogId = comment.addLongProperty("blogId").getProperty();
		comment.addToOne(user, cUserId);
		comment.addToOne(blog, blogId);

		// Now blog has many comments
		// Property commentId = blog.addLongProperty("commentId").getProperty();
		blog.addToMany(comment, blogId);
	}

}