package kiuya.english.game;

public abstract class GameModel{
	Setting set;
	
	String u_word,HisWord;	//u_word記錄玩家輸入單字，HisWord紀錄前一個單字
	boolean hasRecord; //是否記錄電腦單字
	boolean firstInput; //是否第一次輸入
	boolean hasCmd;	//判斷字母是否存在
	boolean findword, findSimilar; //findword是否找到當前單字，findSimilar是否找到和印象單字match的單字
	
	//單人模式其他變數
	int playerHelp,playerComplementWord; //palyerHelp 救援次數,playerComplementWord 印象單字次數
	//多人模式其他變數
	int playersHelp[], playersComplementWord[]; //palyersHelp 記錄各玩家的救援次數,playersComplementWord 記錄各玩家的印象單字次數

    GameModel() {
    	set = new Setting();
    }
    
    public void iniGame(){ //初始化資料
    	playerHelp = StaticVariable.maxHelp;
    	playerComplementWord = StaticVariable.maxComplementWord;
    	firstInput = true; //第一次輸入單字
    	hasCmd = false;	//判斷字母是否存在
    	hasRecord = false;
    	StaticVariable.isExit = false;
    	for(int i=0; i<StaticVariable.hisWord.length; i++)
    		StaticVariable.hisWord[i] = "";
    	
    	//多人模式有其他變數要另外初始化
    	if(StaticVariable.peopleNum > 1){
    		StaticVariable.playersSurrender = new int[StaticVariable.peopleNum];
    		playersHelp = new int[StaticVariable.peopleNum];
    		playersComplementWord = new int[StaticVariable.peopleNum];
    		
    		for(int i=0; i<playersHelp.length; i++){
    			StaticVariable.playersSurrender[i] = 0;
    			playersHelp[i] = StaticVariable.maxHelp;
    			playersComplementWord[i] = StaticVariable.maxComplementWord;
    		}
    	}
    	
    	try{
    		set.setWordArray(); //呼叫Setting的Method設定陣列大小
    		set.inputAlph(); //呼叫Setting的Method設定儲存單字至陣列中
    	}catch(Exception e){
    		StaticVariable.msg = "設定出現問題iniGame";
    	}
    	for(int i=0; i<=25; i++){
    		for(int j=0; j<StaticVariable.wordArr[i].length; j++){
    			StaticVariable.wordArr[i][j][2]="n";
    			StaticVariable.wordArr[i][j][3]="n";
    		}
		}
    }
    
    public void runGame(){
    	try{
    		StartGame();
    	}catch(Exception e){
    		StaticVariable.msg = "執行出現問題runGame";
    	}
    }
    
    public void saveRecord(){
    	set.SaveWordToFile(hasRecord);
    }
    //******************************************************//
    
    //*******************兩個子類別的共同抽象方法*******************//
    abstract void StartGame(); //開始遊戲
    abstract boolean CheckCmd(String word); //檢查輸入的字串是否為指令
    abstract void CheckFindWord(int alphword, int alphindex, boolean errMsg); //檢查是否找到單字
    //******************************************************//

    
    //********************兩個子類別的共同方法********************//
    void userPlay(){ //玩家開始行動
   		u_word = StaticVariable.word;
   		hasCmd = CheckCmd(u_word);	//判斷字母是否為指令
   		if(!hasCmd){
   			if(firstInput){
   				CheckWord(u_word);
   				if(findword && firstInput) firstInput = false;
   			}else{
   				if(CheckHead(u_word)) CheckWord(u_word);
   			}
   		}
	}
    
    boolean CheckHead(String s1){ //檢查字母開頭是否與上一個單字字尾相同
		if(!(s1.equals("noinput"))){
			u_word = ToLowerCase(u_word); //將字母全都轉成小寫比對
			if((GetFirstChar(u_word)).equals(GetLastChar(HisWord))){ //看是否目前單字字首和上一個單字字尾是否相同
				return true;
			}else{
				StaticVariable.msg += "輸入的單字開頭有誤!!";
				return false;
			}
		}
		return true; //表示尚未輸入任何一個單字，所以不用檢查字首
	}
    
    void CheckWord(String s1){ //檢查及印出目前單字
    	int alphword=0,alphindex=0;	//alphword儲存目前字母的單字索引，alphindex儲存目前字母索引
   		boolean errMsg = false; //若值為false表示查無此單字，若為true表示此單字重複使用
   		String headword;
   		
   		if(firstInput && u_word.equals("\\c")){
   			alphindex = (int)(Math.random()*StaticVariable.wordArr.length);
   		}else{
   			if(s1.equals("noinput")) headword = GetLastChar(HisWord);	//取得上一個單字的字尾，當作這次的字首
   			else headword = GetFirstChar(u_word);	//取得輸入單字的字首，當作這次的字首
   		
   			for(int i=0; i<StaticVariable.wordArr.length; i++){	//執行26個字母
   				if(StaticVariable.wordArr[i][0][0].substring(0,1).toLowerCase().equals(headword)){	//判斷目前單字字首為:a~z其中一個
   					alphindex = i;	//紀錄字首的字母索引
   					break;
   				}
   			}
   		}
   		
   		if(s1.equals("noinput")){
   			int index = (int)(Math.random()*StaticVariable.wordArr[alphindex].length);	//隨機產生單字的索引
			while(!(StaticVariable.wordArr[alphindex][index][2].equals("n"))){	//重複執行直到單字沒使用過
				index = (int)(Math.random()*StaticVariable.wordArr[alphindex].length);	//隨機產生單字的索引
			}
			alphword = index;	//將索引存入
			u_word = StaticVariable.wordArr[alphindex][alphword][0];	//將找到單字存入
			findword = true;	//找到單字
   		}else{
   			for(int i=0; i<StaticVariable.wordArr[alphindex].length; i++){
   				if(StaticVariable.wordArr[alphindex][i][0].toLowerCase().equals(s1)){
   					if(StaticVariable.wordArr[alphindex][i][2].equals("n")){
   						alphword = i;
   	   					findword = true;
   	   					break;
   					}else{
   						errMsg = true;
   						StaticVariable.msg += "此單字已被使用過!!";
   					}
   				}
   			}	
   		}
   		this.CheckFindWord(alphword, alphindex, errMsg);
	}
    
    void CheckComplementWord(){
    	findSimilar = false;
    	String word = StaticVariable.playerComplementWord;
    	int alphindex=0;
    	String headword = GetFirstChar(word);
    	for(int i=0; i<StaticVariable.wordArr.length; i++){	//執行26個字母
    		if(StaticVariable.wordArr[i][0][0].substring(0,1).toLowerCase().equals(headword)){	//判斷目前單字字首為:a~z其中一個
    			alphindex = i;	//紀錄字首的字母索引
    			break;
    		}
    	}

    	StaticVariable.msg += "搜尋結果如下:\\n";
    	for(int i=0; i<StaticVariable.wordArr[alphindex].length; i++){
    		CompareWord(word, alphindex, i);
    	}
    	if(!findSimilar) StaticVariable.msg += "很抱歉沒有類似的單字!!";

    }
    
    void CompareWord(String word, int x, int y){
    	int count = 0; //記錄match到的字元有幾個
    	String wordTmp = word.substring(1); //最後比對match次數用的
    	word = word.substring(1); //先去掉字首，因為已經確定字首不需要比
    	String word2 = StaticVariable.wordArr[x][y][0].substring(1); //要被比對的單字，並且去掉字首
    	if((word2.length() == word.length()) || (word2.length() == word.length()+1) || (word2.length() == word.length()-1)){
    		char charArr[] = word2.toCharArray();
    		while(word.length() > 0){
    			for(int i=0; i<charArr.length; i++){
        			if(word.substring(0,1).equals(String.valueOf(charArr[i]))){
        				charArr[i] = ' ';
        				count++;
        				break;
        			}
        		}
    			word = word.substring(1);
    		}
    		if((count == wordTmp.length()) || (count == wordTmp.length()-1)){
    			StaticVariable.msg += StaticVariable.wordArr[x][y][0] + "\\n";
    			findSimilar = true;
    		}
    	}
    }
    
    String ToLowerCase(String s1){
		return s1.toLowerCase();	//將傳入的字轉小寫
	}
    
	String GetFirstChar(String s1){
		return s1.substring(0,1);	//取得傳入字的字首
	}
	
    String GetLastChar(String s1){
    	return s1.substring(s1.length()-1);	//取得目前單字的字尾
    }
    //******************************************************//
    
    
    //********************UserVsCom專有方法********************//
    //                                                      //
    //******************************************************//
    
    
    //********************UserVsUser專有方法*******************//
    //void CheckWinner(); 檢查是否有玩家獲勝                                                                  //
    //void nextUser(); 尋找下一位輸入單字的玩家                                                               //
    //******************************************************//
}