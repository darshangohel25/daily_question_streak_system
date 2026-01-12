import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader; 
import java.io.BufferedWriter;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class QuestionRewardApp
{
    private int coins=0;
    private int streak=0;
    private boolean didtask=false;
    
    public int getCoins() 
    { 
        return coins; 
    }
    
    public void setCoins(int coins) 
    { 
        this.coins = coins; 
    }

    public int getStreak() 
    { 
        return streak; 
    }

    public void setStreak(int streak) 
    { 
        this.streak = streak; 
    }
    
    public boolean getDidtask() 
    { 
        return didtask; 
    }

    public void setDidtask(boolean didtask) 
    { 
        this.didtask = didtask; 
    }
    
    enum Rank 
        { 
            BEGINNER(1), LEARNER(2), CONSISTENT(3), FOCUSED(4), DISCIPLINED(5), UNBREAKABLE(6); 
                private int level; 
                Rank(int level)
                { 
                    this.level = level; 
                }
                
                public int getLevel()
                { 
                    return level; 
                } 
        }

    
    public static void menu() 
    {
        System.out.println("==========MENU OPTION========");
		System.out.println("press 1 to enter a question: ");
		System.out.println("press 2 to view your questions: ");
		System.out.println("press 3 to see your coins: ");
		System.out.println("press 4 to see your streak: ");
		System.out.println("press 5 to see your rank: ");
	    System.out.println("press 6 to exit: ");
	}

    public void saveCoins()
    {
        try {
			 BufferedWriter bw = new BufferedWriter(new FileWriter("coins.txt")); 
			 bw.write(String.valueOf(coins)); 
			 bw.close();
		}
		catch(Exception e)
		{
			System.out.println("Error: " + e.getMessage());
		}
    }
    
    public void loadCoins()
    {
        String ch;
        
        StringBuilder sb = new StringBuilder();
        
        try 
        {
			BufferedReader br  = new BufferedReader(new FileReader("coins.txt"));

			while((ch=br.readLine())!= null)
			{
			   sb.append(ch);
			}
			
			br.close();
			if (sb.length() > 0)
			    coins = Integer.parseInt(sb.toString().trim());
		} 
		catch(Exception e) 
		{
		   coins=0;
		}
    }
    
    public void checkCoinLoad()
    {
        File file = new File("coins.txt"); 
        if (!file.exists()) 
        { 
            coins = 0;
            saveCoins();
            
        }
        else
        {
            loadCoins();
        }
    }
    
    public void saveLastActivity()
    {
        try{
        
        BufferedWriter bw = new BufferedWriter(new FileWriter("last_activity.txt")); 
        bw.write(String.valueOf(LocalDate.now().toString()));
        bw.close();
    }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public LocalDate loadLastActivity() {
    try {
        BufferedReader br  = new BufferedReader(new FileReader("last_activity.txt"));
        StringBuilder sb = new StringBuilder();
        String ch;
        while((ch=br.readLine())!= null) sb.append(ch);
        br.close();
        return LocalDate.parse(sb.toString().trim());
    } 
    catch(Exception e) {
        return null;
    }
}

   public Long diff_days_active()
   {
        LocalDate last_date = loadLastActivity();
        LocalDate now_date = LocalDate.now();
        
        if(last_date==null){
            return null;
        }
        
        return ChronoUnit.DAYS.between(last_date, now_date);
   }
    
   public void applyPenalty(long diffDays)
    {
        System.out.println("You were inactive for " + diffDays + " days");
        
        if(diffDays>0)
        {
            coins = (int)(coins - (diffDays*2));
            if(coins<0)
            {
                coins=0;
            }
            System.out.println("Remaining "+coins+" coins");
            saveCoins();
        }
    }
    
    public void saveStreak()
    {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("save_streak.txt"));
            bw.write(String.valueOf(streak));
            bw.close();
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void loadStreak()
    {
        String ch;
        StringBuilder sb = new StringBuilder();
        try
        {
            BufferedReader br  = new BufferedReader(new FileReader("save_streak.txt"));
            while((ch=br.readLine())!= null)
            {
                sb.append(ch);
            }
            br.close();
            if (sb.length() > 0)
			    streak = Integer.parseInt(sb.toString().trim());
		}
		
		catch(Exception e) 
		{
		   streak=0;
		}
    }
    
    public void checkStreakLoad()
    {
        File file = new File("save_streak.txt"); 
        if (!file.exists()) 
        { 
            streak = 0;
            saveStreak();
            
        }
        else
        {
            loadStreak();
        }
    }
    
    public void saveLastStreakDate()
    {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("save_streak_date.txt"));
            bw.write(LocalDate.now().toString());
            bw.close();
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public LocalDate loadLastStreakDate()
    {
        String ch;
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader br  = new BufferedReader(new FileReader("save_streak_date.txt"));
            while((ch=br.readLine())!= null)
            {
                sb.append(ch);
            }
            br.close();
            
            return LocalDate.parse(sb.toString().trim());
        }
        catch(Exception e)
        {
            return null;
        }

    }
    
    public Long diff_days_streak_active()
    {
        LocalDate last_date = loadLastStreakDate();
        LocalDate now_date = LocalDate.now();
        if(last_date==null){return null;}
        
        return ChronoUnit.DAYS.between(last_date, now_date);
    }
    
    public Rank rank()
    {
        if(coins>=150 && streak>=20){return Rank.UNBREAKABLE;}
        else if(coins>=90 && streak>=12){return Rank.DISCIPLINED;}
        else if(coins>=50 && streak>=7){return Rank.FOCUSED;}
        else if(coins>=25 && streak>=4){return Rank.CONSISTENT;}
        else if(coins>=10 && streak>=2){return Rank.LEARNER;}
        else{return Rank.BEGINNER;}
        
    }
    
    public void save_Rank()
    {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("last_Rank.txt"));
            bw.write(rank().name());
            bw.close();
        }catch(Exception e)
        {
            System.out.println("Error saving rank: " + e.getMessage());
        }
    }
    
    public Rank loadLastRank()
    {
        String ch;
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader br  = new BufferedReader(new FileReader("last_Rank.txt"));
            while((ch=br.readLine())!= null)
            {
                sb.append(ch);
            }
            br.close();
            if(sb.length()>0) { 
                return Rank.valueOf(ch.trim());  
            } 
        } 
        catch(Exception e) 
        { 
            return Rank.BEGINNER;
        } 
        return Rank.BEGINNER;
	}
    
    public int level_Of_Rank(Rank rank)
    {
        return rank.getLevel();
    }
    
    public void details()
    {
        System.out.println("-------------------------------------------------------------");
        System.out.println("coins: "+""+coins+" "+"streak: "+""+streak+" "+"rank: "+rank().name());
        System.out.println("-------------------------------------------------------------");
    }
    
    public void addQuestion(Scanner sc)
    {
        try {
					BufferedWriter bw  = new BufferedWriter(new FileWriter("doubts.txt",true));
					System.out.println("enter your question");
					sc.nextLine();
					String question = sc.nextLine().trim();
					if(question.isEmpty()){System.out.println("Question cannot be empty!");}
					else{
					    bw.write(question + "\n");
					    coins++;
					    saveCoins();
					    save_Rank();
					    didtask = true;
					}
					
					bw.close();
				}
				catch(Exception e)
				{
					System.out.println("Error: " + e.getMessage());
				}
    }
    
    public void viewQuestions()
    {
        String ch;
        try {
			BufferedReader br  = new BufferedReader(new FileReader("doubts.txt"));
			  while((ch=br.readLine())!= null)
				{
				    System.out.print(ch);
				}
				br.close();
				} 
				catch(Exception e)
				{
					System.out.println("Error: " + e.getMessage());
				}
    }
    
    public void showCoins()
    {
        System.out.println("total coins:"+coins);
    }
    
    public void showStreak(){
        System.out.println("your streak is: "+streak);
    }
    
    public void showRank(){
        System.out.println("your rank is: "+rank().name());
    }
    
    
    public void startApp()
	{
	    Scanner sc = new Scanner(System.in);
		
		int choise;
		
		
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
				addQuestion(sc);
				break;

			case 2:
				viewQuestions();
				break;

			case 3:
				showCoins();
				break;
				
			case 4:
			    showStreak();
			    break;
				
			case 5:
			    showRank();
			    break;
			    
		    case 6:
			    System.out.println("Exiting...");
			    break;
			    
			default:
				System.out.println("Invalid choice, please try again");

}
		} while(choise!=6);
		
	}
	
	public void updateStreak()
	{
	    Long gapStreak = diff_days_streak_active();
	    
	    if (didtask) 
        {
            if (gapStreak == null && streak==0) 
            {
                streak = 1;
                
            } 
            
            else if (gapStreak == 1) 
            {
                streak++;
                
            }
            
            else if(gapStreak>1)
            {
                streak=1;
            }
            
            else{}

            saveStreak();
            saveLastStreakDate();
            saveLastActivity();
            save_Rank();
        }
	}
	
	public static void main(String[] args)
	{

        QuestionRewardApp app = new QuestionRewardApp();
        
        app.checkCoinLoad();
        app.checkStreakLoad();
        
        int old_coins = app.coins;
        
        Rank oldRank = app.loadLastRank();

        Long gapActivity = app.diff_days_active();        
         
        if (gapActivity!=null && gapActivity >= 1)
        {
            app.applyPenalty(gapActivity);
            app.saveLastActivity();
        }

        app.details();
        
        app.startApp();
        
        app.updateStreak();
        
        int updated_coins = app.coins; 
        int earn_coins = updated_coins - old_coins;
   
        System.out.printf("you earn %d coin so you move to %d -> %d\n",earn_coins,old_coins,updated_coins);
        System.out.println("Great job! You earned a coin Keep going!");
        Rank newRank = app.rank();

        if (!newRank.equals(oldRank)) 
        { 
            if (newRank.getLevel() < oldRank.getLevel()) 
            { System.out.println("You dropped from " + oldRank.name() + " to " + newRank.name()); 
                
            } 
            else { 
                System.out.println("Rank Up! " + oldRank.name() + " → " + newRank.name()); 
                
            } 
            
        }
        System.out.println(" Streak increased! You are on " + app.streak + " days streak!");


        app.save_Rank();
        app.saveCoins();
        app.saveStreak();
        
        
    }

}
