package com.smartflow.util;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BOMDataForPage {
	private int Id;
    private int MaterialId;
    private String MaterialNumber;//MaterialNumber需要作非空判断
    private int Version;
    private String Name;
    private Date ValidBegin;
    private Date ValidEnd;
    private String Factory;
    public BOMDataForPage(int id, int materialId, String materialNumber, int version, String name, Date validBegin,
			Date validEnd, String creator, Date creationDateTime, String editor, Date editDateTime,String factory) {
		
		Id = id;
		MaterialId = materialId;
		MaterialNumber = materialNumber;
		Version = version;
		Name = name;
		ValidBegin = validBegin;
		ValidEnd = validEnd;
		Creator = creator;
		CreationDateTime = creationDateTime;
		Editor = editor;
		EditDateTime = editDateTime;
		Factory=factory;
	}
    @JsonProperty("Factory")
    public String getFactory() {
		return Factory;
	}
	public void setFactory(String factory) {
		Factory = factory;
	}
	@JsonProperty("Id")
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	@JsonProperty("MaterialId")
	public int getMaterialId() {
		return MaterialId;
	}
	public void setMaterialId(int materialId) {
		MaterialId = materialId;
	}
	@JsonProperty("MaterialNumber")
	public String getMaterialNumber() {
		return MaterialNumber;
	}
	public void setMaterialNumber(String materialNumber) {
		MaterialNumber = materialNumber;
	}
	@JsonProperty("Version")
	public int getVersion() {
		return Version;
	}
	public void setVersion(int version) {
		Version = version;
	}
	@JsonProperty("Name")
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	@JsonProperty("ValidBegin")
	public Date getValidBegin() {
		return ValidBegin;
	}
	public void setValidBegin(Date validBegin) {
		ValidBegin = validBegin;
	}
	@JsonProperty("ValidEnd")
	public Date getValidEnd() {
		return ValidEnd;
	}
	public void setValidEnd(Date validEnd) {
		ValidEnd = validEnd;
	}
	@JsonProperty("Creator")
	public String getCreator() {
		return Creator;
	}
	public void setCreator(String creator) {
		Creator = creator;
	}
	@JsonProperty("CreationDateTime")
	public Date getCreationDateTime() {
		return CreationDateTime;
	}
	public void setCreationDateTime(Date creationDateTime) {
		CreationDateTime = creationDateTime;
	}
	@JsonProperty("Editor")
	public String getEditor() {
		return Editor;
	}
	public void setEditor(String editor) {
		Editor = editor;
	}
	@JsonProperty("EditDateTime")
	public Date getEditDateTime() {
		return EditDateTime;
	}
	public void setEditDateTime(Date editDateTime) {
		EditDateTime = editDateTime;
	}
	private String Creator;
    private Date CreationDateTime;
    private String Editor;
    private Date EditDateTime;

}
