package com.yyq.tool;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.io.IOUtils;

public class ORMTool extends JFrame {
	
	JTextField jmodel;
	JComboBox tables;
	JTextField driverClassName;
	JTextField url;
	JTextField username;
	JTextField password;
	String path;
	
	private static final String copyright="/**\n * @author YYQ ORMTool\n */\n\n";

	public ORMTool() {
		setLayout(new GridLayout(7, 2));// 设置网格布局

		add(new JLabel("driverClassName"));

		driverClassName = new JTextField();
		add(driverClassName);

		add(new JLabel("url"));

		url = new JTextField();
		add(url);

		add(new JLabel("username"));

		username = new JTextField();
		add(username);

		add(new JLabel("password"));

		password = new JTextField();
		add(password);

		add(new JLabel("模块名"));

		jmodel = new JTextField();
		add(jmodel);

		JButton jbutton1 = new JButton("获取数据库信息");
		jbutton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getTables();
			}
		});
		add(jbutton1);

		tables = new JComboBox();
		add(tables);

		JButton jbutton2 = new JButton("生成实体类和Dao");
		jbutton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jmodel.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "请先填写模块名！", "模块名为空！", JOptionPane.WARNING_MESSAGE);
				} else {
					create();
				}

			}
		});
		add(jbutton2);

		add(new JLabel("作者：YYQ"));

		path = System.getProperty("user.dir") + "\\src";
		// 读取properties配置文件的值
		Properties p = new Properties();
		try {
			p.load(getClass().getClassLoader().getResourceAsStream(
					"application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		driverClassName.setText(p.getProperty("jdbc.driver"));
		url.setText(p.getProperty("jdbc.url"));
		username.setText(p.getProperty("jdbc.username"));
		password.setText(p.getProperty("jdbc.password"));

		getTables();

	}

	public static void main(String[] args) {
		ORMTool ormTool = new ORMTool();
		ormTool.setSize(600, 400);
		ormTool.setVisible(true);
		ormTool.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * 获取数据库信息
	 */
	public void getTables() {
		tables.removeAllItems();
		String strsql = "show tables";
		List<Map<String, String>> result = queryMapList(strsql);
		List<String> tableList = new ArrayList<String>();
		for (Map<String, String> map : result) {
			tableList.add(map.get("TABLE_NAME"));
		}
		for (String str : tableList) {
			tables.addItem(str);
		}
	}

	/**
	 * 生成pojo类和dao类
	 */
	private void create() {
		String model = jmodel.getText();
		String table = tables.getSelectedItem().toString();
		String strsql = "show columns from " + table;// select * from all_tables
		// SELECT column_name, data_type, data_length, nullable from
		// user_tab_columns where table_name='ZS_STU'
		List<Map<String, String>> result = queryMapList(strsql);
		// System.out.println(result.get(0).getClass());
		System.out.println(strsql);
		for (Map<String, String> map : result) {
			System.out.println("----------");
			// if("ZSXT".equals(map.get("OWNER"))){
			System.out.println(map.get("TABLE_NAME"));
			for (Map.Entry entity : map.entrySet()) {

				System.out.println("key=" + entity.getKey());
				System.out.println("value=" + entity.getValue());
			}
			// }
		}

		String pojoName = UpperFirst(table);
		String daoName = pojoName + "Dao";

		// 以下生成dao类
		StringBuffer sb = new StringBuffer();
		sb.append("package " + model + ".dao;\n\n");
		sb.append("import org.springframework.stereotype.Service;\n\n");
		sb.append("import " + model + ".pojo." + pojoName + ";\n\n");
		sb.append(copyright);
		sb.append("import com.yyq.dao.MyHibernateDaoSupport;\n\n");
		sb.append("@Service(value=\""+table+"Dao\")\n");
		sb.append("public class " + daoName + " extends MyHibernateDaoSupport<"
				+ pojoName + ">{\n\n");
		sb.append("}");
		try {
			File file = new File(path + "\\" + model + "\\dao\\" + daoName + ".java");
			if (file.exists() == false || JOptionPane.showConfirmDialog(null, "确认覆盖它吗？", "dao类已存在", JOptionPane.YES_NO_OPTION) == 0) {
				if (!file.getParentFile().exists()) {
					if(JOptionPane.showConfirmDialog(null, "确认创建"+model+"模块吗？", "模块不存在", JOptionPane.YES_NO_OPTION) != 0){
						return;
					}
					System.out.println("目标文件所在路径不存在，准备创建。。。");
					if (!file.getParentFile().mkdirs()) {
						System.out.println("创建目录文件所在的目录失败！");
					}
				}

				OutputStream os = new FileOutputStream(file);
				IOUtils.write(sb, os, "UTF-8");
				IOUtils.closeQuietly(os);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 以下生成pojo类
		String catalog = url.getText().substring(url.getText().lastIndexOf("/") + 1);
		sb = new StringBuffer();
		sb.append("package " + model + ".pojo;\n\n");
		sb.append("import javax.persistence.Column;\n");
		sb.append("import javax.persistence.Entity;\n");
		sb.append("import javax.persistence.Table;\n\n");
		sb.append("import com.yyq.dao.IdEntity;\n\n");
		sb.append(copyright);
		sb.append("@Entity\n");
		sb.append("@Table(name = \"" + table + "\", catalog = \"" + catalog + "\")\n");
		sb.append("public class " + pojoName + " extends IdEntity implements java.io.Serializable {\n");

		sb.append(creatField(result));

		sb.append("\n");
		sb.append("\tpublic " + pojoName + "() {\n");
		sb.append("\t}\n");
		sb.append("\n");

		sb.append(creatGetterSetter(result));

		sb.append("}");
		try {
			File file = new File(path + "\\" + model + "\\pojo\\" + pojoName + ".java");
			if (file.exists() == false || JOptionPane.showConfirmDialog(null, "确认覆盖它吗？", "pojo类已存在", JOptionPane.YES_NO_OPTION) == 0) {
				if (!file.getParentFile().exists()) {
					System.out.println("目标文件所在路径不存在，准备创建。。。");
					if (!file.getParentFile().mkdirs()) {
						System.out.println("创建目录文件所在的目录失败！");
					}
				}
				OutputStream os = new FileOutputStream(file);
				IOUtils.write(sb, os, "UTF-8");
				IOUtils.closeQuietly(os);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String UpperFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	private String creatField(List<Map<String, String>> result) {
		String column_name;
		String column_type;
		String type = null;
		StringBuffer sb = new StringBuffer();
		for (Map<String, String> map : result) {
			column_name = map.get("COLUMN_NAME");
			if ("id".equals(column_name)) continue;
			column_type = map.get("COLUMN_TYPE");
			type = getType(column_type);
			sb.append("\tprivate " + type + " " + column_name + ";\n");
		}
		return sb.toString();
	}

	private String getType(String column_type) {
		String type = null;
		if (column_type.startsWith("char") || column_type.startsWith("varchar") || column_type.startsWith("text")) {
			type = "String";
		} else if (column_type.startsWith("int")) {
			type = "Integer";
		} else if (column_type.startsWith("decimal")) {
			type = "Double";
		}
		return type;
	}

	/**
	 * 生成pojo类的GetterSetter方法
	 * 
	 * @param result
	 * @return
	 */
	private String creatGetterSetter(List<Map<String, String>> result) {
		String column_name;
		String column_type;
		String type = null;
		StringBuffer sb = new StringBuffer();
		for (Map<String, String> map : result) {
			column_name = map.get("COLUMN_NAME");
			if ("id".equals(column_name)) continue;
			column_type = map.get("COLUMN_TYPE");
			type = getType(column_type);
			sb.append("\t@Column(name = \"" + column_name + "\"");
			if ("NO".equals(map.get("IS_NULLABLE"))) sb.append(", nullable = false");
			if (type.equals("String")) {
				if ("text".equals(column_type)) {
					sb.append(", length = 65535");
				} else {
					sb.append(", length = " + getLength(column_type));
				}
			}
			if (type.equals("double")) {
				sb.append(", precision = " + getLength(column_type));
			}
			sb.append(")\n");

			sb.append("\tpublic " + type + " get" + UpperFirst(column_name) + "() {\n");
			sb.append("\t\treturn " + column_name + ";\n");
			sb.append("\t}\n\n");

			sb.append("\tpublic void set" + UpperFirst(column_name) + "(" + type + " " + column_name + ") {\n");
			sb.append("\t\tthis." + column_name + " = " + column_name + ";\n");
			sb.append("\t}\n\n");

		}
		return sb.toString();
	}

	/**
	 * 获得字段的长度
	 * 
	 * @param column_type
	 * @return
	 */
	private String getLength(String column_type) {
		int start = column_type.indexOf("(");
		int end = column_type.indexOf(",");
		if (end == -1) end = column_type.indexOf(")");
		return column_type.substring(start + 1, end);
	}

	public List<Map<String, String>> queryMapList(String sql) {
		QueryRunner qr = new QueryRunner();
		ResultSetHandler rsh = new MapListHandler();
		try {
			return (List<Map<String, String>>) qr.query(getConnection(), sql, rsh);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Connection getConnection() {
		// String driverClassName =
		// "com.mysql.jdbc.Driver";//oracle.jdbc.driver.OracleDriver
		// String url =
		// "jdbc:mysql://localhost:3306/yyq?useUnicode=true&amp;characterEncoding=UTF-8";//jdbc:oracle:thin:@192.168.53.25:1521:XMSTC
		// String username = "root";//ZSXT
		// String password = "yyq";//123
		DbUtils.loadDriver(driverClassName.getText());
		try {
			return DriverManager.getConnection(url.getText(), username.getText(),password.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
