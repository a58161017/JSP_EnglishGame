package kiuya.english.game;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.*;

class Setting extends HttpServlet {
	Setting(){
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	//********************�N�]�w�ɪ����eŪ�i��********************//
    void setConfig(){
    	String line;
		String valueArr[];
		try{
			FileReader fr;
			BufferedReader br;
			fr = new FileReader(StaticVariable.realPath+"//confing//charsettings.properties");
			br = new BufferedReader(fr);
    		while((line=br.readLine()) != null){
    			valueArr=line.split("=");
    			judge(valueArr[0], valueArr[1]);
    		}
    		br.close();
    		fr.close();
    		System.out.println("Ū�ɦ��\!!");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Ū�ɥX�{���~!!");
		}
    }
    
    //****************�P�_�������ܼƭȡA�ñN��s�J****************//
    void judge(String key, String value){
    	if(key.equals("MaxHelp"))
    		StaticVariable.maxHelp = Integer.valueOf(value);
    	else if(key.equals("MaxUsers"))
    		StaticVariable.maxUsers = Integer.valueOf(value);
    	else if(key.equals("HistoryWord"))
    		StaticVariable.historyWord = Boolean.valueOf(value);
    	else if(key.equals("ComplementWord"))
    		StaticVariable.complementWord = Boolean.valueOf(value);
    	else if(key.equals("MaxComplementWord"))
    		StaticVariable.maxComplementWord = Integer.valueOf(value);
    	else
    		System.out.println("�]�w�ɦ����D!!");
    }
    
    //*************************�ŧi�}�C�j�p*************************//
    void setWordArray(){
    	Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StaticVariable.wordArr = new String[26][][];
		
		try {
			
			conn = DriverManager.getConnection("jdbc:sqlite:"+StaticVariable.realPath+"//EnglishWord.db");
			conn.setAutoCommit(false);

			stmt = conn.createStatement();
			for(int i=0; i<=25; i++){ //�q�O�ƥ�1�}�l~26�A�����C�Ӧr�����P���}�C�j�p
				char c = (char)(i+97);
				rs = stmt.executeQuery("SELECT COUNT(*) AS COUNT FROM WORD WHERE WORD LIKE '"+c+"%';"); //���o�U�^��r�����U����r�ƶq
				int count = 0;
				while (rs.next()) count = rs.getInt("count");
				StaticVariable.wordArr[i] = new String[count][4]; //�ӧO�ŧi26�Ӧr������r�h��
			}
		} catch (Exception e) {
			System.err.println("���o�u�^���r�ƶq�v����");
		}finally{
			try{
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){}
		}
    }
    
    //*************************�x�s�}�C��r*************************//
    void inputAlph(){
    	Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+StaticVariable.realPath+"//EnglishWord.db");
			conn.setAutoCommit(false);

			stmt = conn.createStatement();
			for(int i=0; i<=25; i++){ //�q�O�ƥ�1�}�l~26�A�����C�Ӧr�����P���}�C�j�p
				char c = (char)(i+97);
				rs = stmt.executeQuery("SELECT WORD,EXPLAIN FROM WORD WHERE WORD LIKE '"+c+"%';");
				int count = 0;
				while (rs.next()) {
					String word = rs.getString("word");
					String explain = rs.getString("explain");
					SaveRowToArray(word,explain,i,count); //�N�r���@��@��s�J�}�C�����
					count++;
				}
			}
			System.out.println("�u�^���r�v�g�J���\!!");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("���o�u�^���r�v����");
		}finally{
			try{
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){}
		}
    }
    void SaveRowToArray(String word,String explain,int wordnum,int count){
    	StaticVariable.wordArr[wordnum][count][0] = word;		//�s�J�r��
    	StaticVariable.wordArr[wordnum][count][1] = explain;	//�s�J�������
    	StaticVariable.wordArr[wordnum][count][2] = "n";		//�s�Jn�N���٨S�Q�ϥιL
    	StaticVariable.wordArr[wordnum][count][3] = "n";		//�s�Jn�N��D����H��J�A0��ܹq���A1��ܪ��a1
    }
    //******************************************************//
    
    //*****************�N�C�����ϥΪ���r�g�J��O�ƥ���****************//
    void SaveWordToFile(boolean hasRecord){
    	Date date = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_hh-mm-ss");
    	
    	if(StaticVariable.historyWord){
    		String name = "HistoryWord_" + formatter.format(date) + ".txt"; //�H�����@�ɦW
    		try{
            	BufferedWriter bw = new BufferedWriter(new FileWriter("./RecordWord/HistoryWord/" + name));
            	for(int i=0; i<StaticVariable.wordArr.length; i++){		
            		for(int j=0; j<StaticVariable.wordArr[i].length; j++){
            			if(StaticVariable.wordArr[i][j][2].equals("y")){
            				bw.write(StaticVariable.wordArr[i][j][0] + " = " + StaticVariable.wordArr[i][j][1]);
            				bw.newLine();
            			}
            		}
            	}
            	bw.close();
        	}catch(Exception e){
        		System.out.println("�g��1�X�{���~!!");
        	}
    	}
    	
    	if(hasRecord){
    		String name = "ComputerWord_" + formatter.format(date) + ".txt"; //�H�����@�ɦW
    		try{
            	BufferedWriter bw = new BufferedWriter(new FileWriter("./RecordWord/ComputerWord/" + name));
            	for(int i=0; i<StaticVariable.wordArr.length; i++){		
            		for(int j=0; j<StaticVariable.wordArr[i].length; j++){
            			if(StaticVariable.wordArr[i][j][3].equals("0")){
            				bw.write(StaticVariable.wordArr[i][j][0] + " = " + StaticVariable.wordArr[i][j][1]);
            				bw.newLine();
            			}
            		}
            	}
            	bw.close();
        	}catch(Exception e){
        		System.out.println("�g��2�X�{���~!!");
        	}
    	}
    }
}