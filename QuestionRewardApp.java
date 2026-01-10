import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class QuestionRewardApp
{
    private int coins=0;
    private int streak=0;
    private boolean didtask=false;
   
    public static void menu() 
    {
        System.out.println("==========MENU OPTION========");
		System.out.println("press 1 for enter the question: ");
		System.out.println("press 2 for see the question: ");
		System.out.println("press 3 for see your coins: ");
		System.out.println("press 4 for see your streak: ");
		System.out.println("press 5 for see your rank: ");
	    System.out.println("press 6 for exit: ");
	}

    public void saveCoins()
    {
        try {
			 FileWriter fwsc = new FileWriter("coins.txt");
			 fwsc.write(String.valueOf(coins));
			 fwsc.close();
		}
		catch(Exception e)
		{
			System.out.println("sonething went wrong..");
		}
    }
    
    public void loadCoins()
    {
        int ch;
        
        StringBuffer sb = new StringBuffer();
        
        try 
        {
			FileReader fr  = new FileReader("coins.txt");

			while((ch=fr.read())!=-1)
			{
			   sb.append((char)ch);
			}
			
			fr.close();
			if (sb.length() > 0)
			    coins = Integer.parseInt(sb.toString().trim());
		} 
		catch(Exception e) 
		{
		   coins=0;
		}
    }
    
    public void saveLastActivity()
    {
        try{
        
        FileWriter fwsla = new FileWriter("last_activity.txt");
        fwsla.write(String.valueOf(LocalDate.now().toString()));
        fwsla.close();
    }catch(Exception e){}
    }
    
    public LocalDate loadLastActivity() {
    try {
        FileReader fr = new FileReader("last_activity.txt");
        StringBuilder sb = new StringBuilder();
        int ch;
        while((ch = fr.read()) != -1) sb.append((char)ch);
        fr.close();
        return LocalDate.parse(sb.toString().trim());
    } catch(Exception e) {
        return LocalDate.now();
    }
}

   public long diif_days_active()
   {
        LocalDate last_date = loadLastActivity();
        LocalDate now_date = LocalDate.now();
        
        return ChronoUnit.DAYS.between(last_date, now_date);
   }
    
   public void applyPenalty(long diffDays)
    {
        System.out.println("you come within a "+diffDays+" days");
        
        if(diffDays>0)
        {
            coins = (int)(coins - (diffDays*2));
            if(coins<0)
            {
                coins=0;
            }
            System.out.println("remaning a "+coins+" coins");
            saveCoins();
        }
    }
    
    public void saveStreak()
    {
        try{
            FileWriter fwss = new FileWriter("save_streak.txt");
            fwss.write(String.valueOf(streak));
            fwss.close();
        }
        catch(Exception e){}
    }
    
    public void loadStreak()
    {
        int ch;
        StringBuilder sb = new StringBuilder();
        try{
        FileReader frls = new FileReader("save_streak.txt");
        while((ch=frls.read())!=-1)
        {
            sb.append((char)ch);
        }
        frls.close();
        if (sb.length() > 0)
			streak = Integer.parseInt(sb.toString().trim());
		}
		
		catch(Exception e) 
		{
		   streak=0;
		}
    }
    
    public void saveLastStreakDate()
    {
        try{
            FileWriter fwls = new FileWriter("save_streak_date");
            fwls.write(LocalDate.now().toString());
            fwls.close();
        }catch(Exception e){}
    }
    
    public LocalDate loadLastStreakDate()
    {
        int ch;
        StringBuilder sb = new StringBuilder();
        try{
            FileReader frlls = new FileReader("save_streak_date");
            while((ch=frlls.read())!=-1)
            {
                sb.append((char)ch);
            }
            frlls.close();
            return LocalDate.parse(sb.toString().trim());
            
        }
        catch(Exception e){return LocalDate.now();}
    }
    
    public long diff_days_streak_active()
    {
        LocalDate last_date = loadLastStreakDate();
        LocalDate now_date = LocalDate.now();
        
        return ChronoUnit.DAYS.between(last_date, now_date);
    }
    
    public String rank()
    {
        if(coins>=150 && streak>=20){return "UNBREAKABLE";}
        else if(coins>=90 && streak>=12){return "DISCIPLINED";}
        else if(coins>=50 && streak>=7){return "FOCUSED";}
        else if(coins>=25 && streak>=4){return "CONSISTENT";}
        else if(coins>=10 && streak>=2){return "LEARNER";}
        else{return "BEGINNER";}
        
    }
    
    public void save_Rank()
    {
        
        try{
            FileWriter fwsr = new FileWriter("last_Rank");
            fwsr.write(rank());
            fwsr.close();
        }catch(Exception e){}
    }
    
    public String loadLastRank()
    {
        int ch;
        StringBuilder sb = new StringBuilder();
        try{
            FileReader frlr = new FileReader("last_Rank");
            while((ch=frlr.read())!=-1)
            {
                sb.append((char)ch);
            }
            frlr.close();
            return sb.toString();
           }catch(Exception e) 
		{
		   return "BEGINNER";
		}
		
    }
    
    public static  int leval_Of_Rank(String rank)
    {
        switch(rank)
        {
            case "UNBREAKABLE":return 6;
            case "DISCIPLINED":return 5;
            case "FOCUSED":return 4;
            case "CONSISTENT":return 3;
            case "LEARNER":return 2;
            case "BEGINNER":return 1;
        }
        return 0;
    }
    
    public void details()
    {
        System.out.println("-------------------------------------------------------------");
        System.out.println("coins: "+""+coins+" "+"streak: "+""+streak+" "+"rank: "+rank());
        System.out.println("-------------------------------------------------------------");
    }
    
   	public void startApp()
	{
	    Scanner sc = new Scanner(System.in);
		
		int choise;
		int ch;
		
	    do
		{
		    menu();
            System.out.print("Enter your choice: ");

            try {
                choise = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter only numbers.");
                sc.nextLine();   
                choise = -1;
                continue;
            }

			
		switch(choise)
			{
			
			case 1:
				try {
					FileWriter fwd = new FileWriter("doubts.txt",true);
					System.out.println("enter your question");
					sc.nextLine();
					String question = sc.nextLine();
					if(question.trim().isEmpty()){System.out.println("do not enter space");}
					else{
					    fwd.write(question + "\n");
					    coins++;
					    saveCoins();
					    didsomething = true;
					}
					
					fwd.close();
				}
				catch(Exception e)
				{
					System.out.println("something went wrong! due to"+e);
				}
				break;

			case 2:
				try {
					FileReader fr = new FileReader("doubts.txt");
					while((ch=fr.read())!=-1)
					{
						System.out.print((char)ch);
					}
					fr.close();
				} catch(Exception e)
				{
					System.out.println("something went wrong! due to"+e);
				}
				break;

			case 3:
				System.out.println("totle coins is:"+coins);
				break;
				
			case 4:
			    System.out.println("your streak is: "+streak);
			    break;
				
			case 5:
			    System.out.println("your rank is: "+rank());
			    break;
			    
		    case 6:
			    System.out.println("Exiting...");
			    break;
			    
			default:
				System.out.println("you enter wrong choise please try again");

}
		} while(choise!=6);
		
	}
	public static void main(String[] args) {

    QuestionRewardApp app = new QuestionRewardApp();

    app.loadCoins();
    app.loadStreak();
    String oldRank = app.loadLastRank();

    long gapActivity = app.diif_days_active();
    long gapStreak = app.diff_days_streak_active();

    if(gapStreak >= 2){
        app.streak = 0;
        app.saveStreak();
    }
    
    app.applyPenalty(gapActivity);
    
    app.details();
    app.startApp();

    if(app.didtask){

        if(gapStreak == 1){
            app.streak++;
            app.saveStreak();
            app.saveLastStreakDate();
        }
        app.saveLastActivity();

    }
    
    String newRank = app.rank();

    if(!newRank.equals(oldRank)){
        if(leval_Of_Rank(newRank) < leval_Of_Rank(oldRank))
            System.out.println("You dropped from " + oldRank + " to " + newRank);
        else
            System.out.println("Rank Up! " + oldRank + " → " + newRank);
    }

    app.save_Rank();
    app.saveStreak();
    app.saveCoins();
    app.saveLastActivity();
}
}



