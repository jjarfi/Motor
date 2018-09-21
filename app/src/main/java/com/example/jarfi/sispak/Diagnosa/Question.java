package com.example.jarfi.sispak.Diagnosa;

public class Question {
	private int ID;
	private String QUESTION;
	private String OPTA;
	private String OPTB;
	private String OPTC;
	private String OPTD;
	private String OPTE;
	private String OPTF;
	private double ANSWER;
	
	public Question()
	{
		ID=0;
		QUESTION="";
		OPTA="";
		OPTB="";
		OPTC="";
		OPTD="";
		OPTE="";
		OPTF="";
		ANSWER=0;
	}
	public Question(String qUESTION, String oPTA, String oPTB, String oPTC, String oPTD, String oPTE, String oPTF, Double aNSWER) {
		
		QUESTION = qUESTION;
		OPTA = oPTA;
		OPTB = oPTB;
		OPTC = oPTC;
		OPTD = oPTD;
		OPTE = oPTE;
		OPTF = oPTF;
		ANSWER = aNSWER;
	}
	public int getID()
	{
		return ID;
	}
	public String getQUESTION() {
		return QUESTION;
	}
	public String getOPTA() {
		return OPTA;
	}
	public String getOPTB() {
		return OPTB;
	}
	public String getOPTC() {
		return OPTC;
	}
	public String getOPTD() {
		return OPTD;
	}
	public String getOPTE() {
		return OPTE;
	}
	public String getOPTF() {
		return OPTF;
	}
	public Double getANSWER() {
		return ANSWER;
	}
	public void setID(int id)
	{
		ID=id;
	}
	public void setQUESTION(String qUESTION) {
		QUESTION = qUESTION;
	}
	public void setOPTA(String oPTA) {
		OPTA = oPTA;
	}
	public void setOPTB(String oPTB) {
		OPTB = oPTB;
	}
	public void setOPTC(String oPTC) {
		OPTC = oPTC;
	}
	public void setOPTD(String oPTD) {
		OPTD = oPTD;
	}
	public void setOPTE(String oPTE) {
		OPTE = oPTE;
	}
	public void setOPTF(String oPTF) {
		OPTF = oPTF;
	}
	public void setANSWER(Double aNSWER) {
		ANSWER = aNSWER;
	}
	
}