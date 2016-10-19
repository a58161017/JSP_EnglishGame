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
	//********************將設定檔的內容讀進來********************//
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
    		System.out.println("讀檔成功!!");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("讀檔出現錯誤!!");
		}
    }
    
    //****************判斷對應的變數值，並將其存入****************//
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
    		System.out.println("設定檔有問題!!");
    }
    
    //*************************宣告陣列大小*************************//
    void setWordArray(){
    	Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StaticVariable.wordArr = new String[26][][];
		
		try {
			
			conn = DriverManager.getConnection("jdbc:sqlite:"+StaticVariable.realPath+"//EnglishWord.db");
			conn.setAutoCommit(false);

			stmt = conn.createStatement();
			for(int i=0; i<=25; i++){ //從記事本1開始~26，給予每個字母不同的陣列大小
				char c = (char)(i+97);
				rs = stmt.executeQuery("SELECT COUNT(*) AS COUNT FROM WORD WHERE WORD LIKE '"+c+"%';"); //取得各英文字首底下的單字數量
				int count = 0;
				while (rs.next()) count = rs.getInt("count");
				StaticVariable.wordArr[i] = new String[count][4]; //個別宣告26個字母的單字多寡
			}
		} catch (Exception e) {
			System.err.println("取得「英文單字數量」失敗");
		}finally{
			try{
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){}
		}
    }
    
    //*************************儲存陣列單字*************************//
    void inputAlph(){
    	Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+StaticVariable.realPath+"//EnglishWord.db");
			conn.setAutoCommit(false);

			stmt = conn.createStatement();
			for(int i=0; i<=25; i++){ //從記事本1開始~26，給予每個字母不同的陣列大小
				char c = (char)(i+97);
				rs = stmt.executeQuery("SELECT WORD,EXPLAIN FROM WORD WHERE WORD LIKE '"+c+"%';");
				int count = 0;
				while (rs.next()) {
					String word = rs.getString("word");
					String explain = rs.getString("explain");
					SaveRowToArray(word,explain,i,count); //將字母一行一行存入陣列的函數
					count++;
				}
			}
			System.out.println("「英文單字」寫入成功!!");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("取得「英文單字」失敗");
		}finally{
			try{
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){}
		}
    }
    void SaveRowToArray(String word,String explain,int wordnum,int count){
    	StaticVariable.wordArr[wordnum][count][0] = word;		//存入字母
    	StaticVariable.wordArr[wordnum][count][1] = explain;	//存入中文註解
    	StaticVariable.wordArr[wordnum][count][2] = "n";		//存入n代表還沒被使用過
    	StaticVariable.wordArr[wordnum][count][3] = "n";		//存入n代表非任何人輸入，0表示電腦，1表示玩家1
    }
    //******************************************************//
    
    //*****************將遊戲中使用的單字寫入到記事本中****************//
    void SaveWordToFile(boolean hasRecord){
    	Date date = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_hh-mm-ss");
    	
    	if(StaticVariable.historyWord){
    		String name = "HistoryWord_" + formatter.format(date) + ".txt"; //以日期當作檔名
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
        		System.out.println("寫檔1出現錯誤!!");
        	}
    	}
    	
    	if(hasRecord){
    		String name = "ComputerWord_" + formatter.format(date) + ".txt"; //以日期當作檔名
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
        		System.out.println("寫檔2出現錯誤!!");
        	}
    	}
    }
}