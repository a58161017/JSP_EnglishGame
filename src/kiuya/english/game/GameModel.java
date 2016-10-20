package kiuya.english.game;

public abstract class GameModel{
	Setting set;
	
	String u_word,HisWord;	//u_word�O�����a��J��r�AHisWord�����e�@�ӳ�r
	boolean hasRecord; //�O�_�O���q����r
	boolean firstInput; //�O�_�Ĥ@����J
	boolean hasCmd;	//�P�_�r���O�_�s�b
	boolean findword, findSimilar; //findword�O�_����e��r�AfindSimilar�O�_���M�L�H��rmatch����r
	
	//��H�Ҧ���L�ܼ�
	int playerHelp,playerComplementWord; //palyerHelp �ϴ�����,playerComplementWord �L�H��r����
	//�h�H�Ҧ���L�ܼ�
	int playersHelp[], playersComplementWord[]; //palyersHelp �O���U���a���ϴ�����,playersComplementWord �O���U���a���L�H��r����

    GameModel() {
    	set = new Setting();
    }
    
    public void iniGame(){ //��l�Ƹ��
    	playerHelp = StaticVariable.maxHelp;
    	playerComplementWord = StaticVariable.maxComplementWord;
    	firstInput = true; //�Ĥ@����J��r
    	hasCmd = false;	//�P�_�r���O�_�s�b
    	hasRecord = false;
    	StaticVariable.isExit = false;
    	for(int i=0; i<StaticVariable.hisWord.length; i++)
    		StaticVariable.hisWord[i] = "";
    	
    	//�h�H�Ҧ�����L�ܼƭn�t�~��l��
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
    		set.setWordArray(); //�I�sSetting��Method�]�w�}�C�j�p
    		set.inputAlph(); //�I�sSetting��Method�]�w�x�s��r�ܰ}�C��
    	}catch(Exception e){
    		StaticVariable.msg = "�]�w�X�{���DiniGame";
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
    		StaticVariable.msg = "����X�{���DrunGame";
    	}
    }
    
    public void saveRecord(){
    	set.SaveWordToFile(hasRecord);
    }
    //******************************************************//
    
    //*******************��Ӥl���O���@�P��H��k*******************//
    abstract void StartGame(); //�}�l�C��
    abstract boolean CheckCmd(String word); //�ˬd��J���r��O�_�����O
    abstract void CheckFindWord(int alphword, int alphindex, boolean errMsg); //�ˬd�O�_����r
    //******************************************************//

    
    //********************��Ӥl���O���@�P��k********************//
    void userPlay(){ //���a�}�l���
   		u_word = StaticVariable.word;
   		hasCmd = CheckCmd(u_word);	//�P�_�r���O�_�����O
   		if(!hasCmd){
   			if(firstInput){
   				CheckWord(u_word);
   				if(findword && firstInput) firstInput = false;
   			}else{
   				if(CheckHead(u_word)) CheckWord(u_word);
   			}
   		}
	}
    
    boolean CheckHead(String s1){ //�ˬd�r���}�Y�O�_�P�W�@�ӳ�r�r���ۦP
		if(!(s1.equals("noinput"))){
			u_word = ToLowerCase(u_word); //�N�r�������ন�p�g���
			if((GetFirstChar(u_word)).equals(GetLastChar(HisWord))){ //�ݬO�_�ثe��r�r���M�W�@�ӳ�r�r���O�_�ۦP
				return true;
			}else{
				StaticVariable.msg += "��J����r�}�Y���~!!";
				return false;
			}
		}
		return true; //��ܩ|����J����@�ӳ�r�A�ҥH�����ˬd�r��
	}
    
    void CheckWord(String s1){ //�ˬd�ΦL�X�ثe��r
    	int alphword=0,alphindex=0;	//alphword�x�s�ثe�r������r���ޡAalphindex�x�s�ثe�r������
   		boolean errMsg = false; //�Y�Ȭ�false��ܬd�L����r�A�Y��true��ܦ���r���ƨϥ�
   		String headword;
   		
   		if(firstInput && u_word.equals("\\c")){
   			alphindex = (int)(Math.random()*StaticVariable.wordArr.length);
   		}else{
   			if(s1.equals("noinput")) headword = GetLastChar(HisWord);	//���o�W�@�ӳ�r���r���A��@�o�����r��
   			else headword = GetFirstChar(u_word);	//���o��J��r���r���A��@�o�����r��
   		
   			for(int i=0; i<StaticVariable.wordArr.length; i++){	//����26�Ӧr��
   				if(StaticVariable.wordArr[i][0][0].substring(0,1).toLowerCase().equals(headword)){	//�P�_�ثe��r�r����:a~z�䤤�@��
   					alphindex = i;	//�����r�����r������
   					break;
   				}
   			}
   		}
   		
   		if(s1.equals("noinput")){
   			int index = (int)(Math.random()*StaticVariable.wordArr[alphindex].length);	//�H�����ͳ�r������
			while(!(StaticVariable.wordArr[alphindex][index][2].equals("n"))){	//���ư��檽���r�S�ϥιL
				index = (int)(Math.random()*StaticVariable.wordArr[alphindex].length);	//�H�����ͳ�r������
			}
			alphword = index;	//�N���ަs�J
			u_word = StaticVariable.wordArr[alphindex][alphword][0];	//�N����r�s�J
			findword = true;	//����r
   		}else{
   			for(int i=0; i<StaticVariable.wordArr[alphindex].length; i++){
   				if(StaticVariable.wordArr[alphindex][i][0].toLowerCase().equals(s1)){
   					if(StaticVariable.wordArr[alphindex][i][2].equals("n")){
   						alphword = i;
   	   					findword = true;
   	   					break;
   					}else{
   						errMsg = true;
   						StaticVariable.msg += "����r�w�Q�ϥιL!!";
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
    	for(int i=0; i<StaticVariable.wordArr.length; i++){	//����26�Ӧr��
    		if(StaticVariable.wordArr[i][0][0].substring(0,1).toLowerCase().equals(headword)){	//�P�_�ثe��r�r����:a~z�䤤�@��
    			alphindex = i;	//�����r�����r������
    			break;
    		}
    	}

    	StaticVariable.msg += "�j�M���G�p�U:\\n";
    	for(int i=0; i<StaticVariable.wordArr[alphindex].length; i++){
    		CompareWord(word, alphindex, i);
    	}
    	if(!findSimilar) StaticVariable.msg += "�ܩ�p�S����������r!!";

    }
    
    void CompareWord(String word, int x, int y){
    	int count = 0; //�O��match�쪺�r�����X��
    	String wordTmp = word.substring(1); //�̫���match���ƥΪ�
    	word = word.substring(1); //���h���r���A�]���w�g�T�w�r�����ݭn��
    	String word2 = StaticVariable.wordArr[x][y][0].substring(1); //�n�Q��諸��r�A�åB�h���r��
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
		return s1.toLowerCase();	//�N�ǤJ���r��p�g
	}
    
	String GetFirstChar(String s1){
		return s1.substring(0,1);	//���o�ǤJ�r���r��
	}
	
    String GetLastChar(String s1){
    	return s1.substring(s1.length()-1);	//���o�ثe��r���r��
    }
    //******************************************************//
    
    
    //********************UserVsCom�M����k********************//
    //                                                      //
    //******************************************************//
    
    
    //********************UserVsUser�M����k*******************//
    //void CheckWinner(); �ˬd�O�_�����a���                                                                  //
    //void nextUser(); �M��U�@���J��r�����a                                                               //
    //******************************************************//
}