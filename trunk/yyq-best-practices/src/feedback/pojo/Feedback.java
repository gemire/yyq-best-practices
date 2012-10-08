package feedback.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yyq.dao.IdEntity;

/**
 * @author YYQ ORMTool
 */

@Entity
@Table(name = "feedback", catalog = "yyq")
public class Feedback extends IdEntity implements java.io.Serializable {
	private String name;
	private String email;
	private String linkTel;
	private String title;
	private String content;
	private String answer;
	private String user__id;
	private String isOK;

	public Feedback() {
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "linkTel", length = 50)
	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	@Column(name = "title", nullable = false, length = 50)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", length = 65535)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "answer", length = 65535)
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name = "user__id", length = 22)
	public String getUser__id() {
		return user__id;
	}

	public void setUser__id(String user__id) {
		this.user__id = user__id;
	}

	@Column(name = "isOK", nullable = false, length = 2)
	public String getIsOK() {
		return isOK;
	}

	public void setIsOK(String isOK) {
		this.isOK = isOK;
	}

}