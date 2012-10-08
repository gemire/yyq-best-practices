package mp3.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yyq.dao.IdEntity;

/**
 * @author YYQ ORMTool
 */

@Entity
@Table(name = "mp3", catalog = "yyq")
public class Mp3 extends IdEntity implements java.io.Serializable {
	private String srcFile;
	private String dir;
	private String fileName;
	private String displayName;

	public Mp3() {
	}

	@Column(name = "srcFile", nullable = false, length = 255)
	public String getSrcFile() {
		return srcFile;
	}

	public void setSrcFile(String srcFile) {
		this.srcFile = srcFile;
	}

	@Column(name = "dir", nullable = false, length = 255)
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	@Column(name = "fileName", nullable = false, length = 255)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "displayName", nullable = false, length = 255)
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}