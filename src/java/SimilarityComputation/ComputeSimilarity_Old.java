/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimilarityComputation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Hamza
 */
public class ComputeSimilarity_Old
{
    public class values
    {
        int val1;
        int val2;
        
        values(int v1, int v2)
        {
            this.val1=v1;
            this.val2=v2;
        }
        
        public void Update_VAl(int v1, int v2)
        {
            this.val1=v1;
            this.val2=v2;
        }
     }//end of class values
   
    String[] HtmlCodes;
  
    double maxCosine=0.0;
    double maxSim=0.0;
    double maxJaccard=0.0;
    double mazMyCosine=0.0;   
    int lengthSentece=0;
    boolean flag =false;


    public   List<String> removeEmptySpace(List<String> input)
    {
        List<String> temp  = new ArrayList<String>();
        for(int i=0 ;i<input.size();i++)
        {
            if(input.get(i).equals("") == false )
            {
                temp.add(input.get(i));
            }
        }
        return temp;
    }
    
    public void buildHtmlCode()
    {
        String htmlString= "&lt;	&amp;\n" +
                            "&gt;	&quot;\n" +
                            "&nbsp;	&mdash;\n" +
                            "&emsp;	&ndash;\n" +
                            "&ensp;	&minus;\n" +
                            "&thinsp;	&oline;\n" +
                            "&cent;	&middot;\n" +
                            "&pound;	&bull;\n" +
                            "&euro;	&copy;\n" +
                            "&sect;	&reg;\n" +
                            "&dagger;	&trade;\n" +
                            "&Dagger;	&iquest;\n" +
                            "&lsquo;	&iexcl;\n" +
                            "&rsquo;	&Aring;\n" +
                            "&#x263a;	&hellip;\n" +
                            "&#x2605;	&#x2295;\n" +
                            "&#x2606;	&#x2299;\n" +
                            "&#x2610;	&#x2640;\n" +
                            "&aelig;	&#x2642;\n" +
                            "&ccedil;	&ouml;\n" +
                            "&ntilde;	&Ccedil;\n" +
                            "&acirc;	&Ntilde;\n" +
                            "&aacute;	&Acirc;\n" +
                            "&agrave;	&Aacute;\n" +
                            "&oslash;	&Agrave;\n" +
                            "&clubs;	&Oslash;\n" +
                            "&spades;	&hearts;\n" +
                            "&lt;	&diams;\n" +
                            "&le;	&loz;\n" +
                            "&gt;	&middot;\n" +
                            "&ge;	&sdot;\n" +
                            "&ne;	&bull;\n" +
                            "&asymp;	&minus;\n" +
                            "&equiv;	&times;\n" +
                            "&cong;	&divide;\n" +
                            "&prop;	&frasl;\n" +
                            "&there4;	&plusmn;\n" +
                            "&sum;	&deg;\n" +
                            "&prod;	&lfloor;\n" +
                            "&prime;	&rfloor;\n" +
                            "&Prime;	&lceil;\n" +
                            "&Delta;	&rceil;\n" +
                            "&nabla;	&lowast;\n" +
                            "&part;	&oplus;\n" +
                            "&int;	&otimes;  |";
        
        htmlString = htmlString.replace('\n', ' ');
        HtmlCodes= htmlString.split("\\s+");
    }
    
    public ComputeSimilarity_Old()
    {
        buildHtmlCode();   
        
    }
    public void computeSimility(String suspesiousFilePath,String LocalDocument ,boolean removeStopWordFlag)
    {
        try
        {
            flag =false;            
            String currentSespioiousSentence ="";
            FileInputStream inputFile = new FileInputStream (suspesiousFilePath);
            InputStreamReader allFileText = new InputStreamReader ( inputFile, "UTF-8" );
            BufferedReader buffer = new BufferedReader(allFileText);
            String suspiousSentences ="";
            while ( ( currentSespioiousSentence = buffer.readLine()) != null )
            {
              suspiousSentences += currentSespioiousSentence+"\n" ; 
            }
           // String[]tokens = AllSentences.split(":|\\."); 
            String[]tokensSespisious = suspiousSentences.split("\n"); 
            List<String> listSespisiousSentences = new ArrayList<String>();
            listSespisiousSentences=Arrays.asList(tokensSespisious);
            listSespisiousSentences= removeEmptySpace(listSespisiousSentences);
            listSespisiousSentences= removelessthan5(listSespisiousSentences);  
            listSespisiousSentences= Normalization(listSespisiousSentences);
            if(removeStopWordFlag==true)
            {
               listSespisiousSentences= removeStopWords( listSespisiousSentences);
            }

            for(int sentenceSespioiosIndex=0;sentenceSespioiosIndex<listSespisiousSentences.size();sentenceSespioiosIndex++)
            {
                maxCosine =0.0;
                maxSim= 0.0 ;
                maxJaccard=0.0;     
                mazMyCosine=0.0;

                currentSespioiousSentence =listSespisiousSentences.get(sentenceSespioiosIndex);
                currentSespioiousSentence= currentSespioiousSentence.trim();
                tokensSespisious = currentSespioiousSentence.split("\\s+");        
                List<String> listSespioiousWord = new ArrayList<String>();
                listSespioiousWord=Arrays.asList(tokensSespisious);
                lengthSentece= listSespioiousWord.size();
                FileInputStream localDocumentFile = new FileInputStream (LocalDocument);
                InputStreamReader allFileText2 = new InputStreamReader ( localDocumentFile, "UTF-8" );
                BufferedReader buffer2 = new BufferedReader(allFileText2);
                String currentLocalSentence= "";
                String localSentences ="";
                
                while ( ( currentLocalSentence = buffer2.readLine()) != null )
                {
                    localSentences += currentLocalSentence+" ";   
                }
                
                String[] tokensLocal = localSentences.split(":|\\.");        
                List<String> listLocalSentences = new ArrayList<String>();
                listLocalSentences=Arrays.asList(tokensLocal);
                listLocalSentences= removeEmptySpace(listLocalSentences);
                listLocalSentences= removelessthan5(listLocalSentences);
                listLocalSentences= Normalization(listLocalSentences);  
                if(removeStopWordFlag==true)
                {
                    listLocalSentences= removeStopWords(listLocalSentences);
                }

                for(int sentenceLocal=0 ; sentenceLocal<listLocalSentences.size(); sentenceLocal++)
                {
                    currentLocalSentence = listLocalSentences.get(sentenceLocal);
                    currentLocalSentence= currentLocalSentence.trim();
                    currentLocalSentence = clearSentence(currentLocalSentence);
                    if(currentLocalSentence.trim().equals(""))
                    {
                        continue;
                    }
                    double cosine=Cosine_Similarity_Score(currentSespioiousSentence,currentLocalSentence);
                    double MyJcardSimility=MyJcardSimility(currentSespioiousSentence, currentLocalSentence);
                    double Jacard=jaccardCimilarity(currentSespioiousSentence, currentLocalSentence);
                    double myCosine = my_Cosine_Similarity_Score(currentSespioiousSentence, currentLocalSentence);
                    if (cosine > maxCosine)
                    {
                        maxCosine = cosine;
                    }                                 
                    if (MyJcardSimility > maxSim)
                    {
                        maxSim = MyJcardSimility;
                    }
                    if (Jacard > maxJaccard)
                    {
                        maxJaccard = Jacard;
                    }
                    if (myCosine > mazMyCosine)
                    {
                        mazMyCosine = myCosine;
                    }
                }
            }
        }
        catch(Exception e)
        {

        }
    }
    
    private double jaccardCimilarity(String S1, String S2) 
    {
        String [] word_seq_text1 = S1.split("\\s+");
        String [] word_seq_text2 = S2.split("\\s+");
        Set<String> Distinct_words_text1 = new HashSet<String>();
        Set<String> Distinct_words_text2 = new HashSet<String>();
        
        //prepare word frequency vector by using Text1
        for(int i=0;i<word_seq_text1.length;i++)
        {
            String tmp_wd = word_seq_text1[i].trim();
            if(tmp_wd.length()>0)      
            {
              Distinct_words_text1.add(tmp_wd);
            }
        }

        for(int i=0;i<word_seq_text2.length;i++)
        {
            String tmp_wd = word_seq_text2[i].trim();
            if(tmp_wd.length()>0)       
            {
              Distinct_words_text2.add(tmp_wd);
            }
        }
          

        
        if( Distinct_words_text1.size() == 0 || Distinct_words_text2.size() == 0 ) 
        {
            return 0.0;
        }
       
        Set<String> unionXY = new HashSet<String>(Distinct_words_text1);
        unionXY.addAll(Distinct_words_text2);
       
        Set<String> intersectionXY = new HashSet<String>(Distinct_words_text1);
        intersectionXY.retainAll(Distinct_words_text2);

        return (double) intersectionXY.size() / (double) unionXY.size();
    }
    
    private double MyJcardSimility(String S1, String S2) 
    {
       
        String [] word_seq_text1 = S1.split("\\s+");
        String [] word_seq_text2 = S2.split("\\s+");
        Set<String> Distinct_words_text1 = new HashSet<String>();
        Set<String> Distinct_words_text2 = new HashSet<String>();
        
        //prepare word frequency vector by using Text1
        for(int i=0;i<word_seq_text1.length;i++)
        {
            String tmp_wd = word_seq_text1[i].trim();
            if(tmp_wd.length()>0)          
            {
                Distinct_words_text1.add(tmp_wd);
            }
        }
    
        for(int i=0;i<word_seq_text2.length;i++)
        {
            String tmp_wd = word_seq_text2[i].trim();
            if(tmp_wd.length()>0)       
            {
                Distinct_words_text2.add(tmp_wd);
            }
        }

        if( Distinct_words_text1.size() == 0 || Distinct_words_text2.size() == 0 ) 
        {
            return 0.0;
        }
        
        Set<String> DifreanateXY = new HashSet<String>(Distinct_words_text1);
        DifreanateXY.removeAll(Distinct_words_text2);
       
        Set<String> intersectionXY = new HashSet<String>(Distinct_words_text1);
        intersectionXY.retainAll(Distinct_words_text2);

        return (double) intersectionXY.size() / ((double) DifreanateXY.size()+(double) intersectionXY.size());
    }

    
    public double my_Cosine_Similarity_Score(String Text1, String Text2)
    {
        double sim_score=0.0000000;

        try
        {
            //1. Identify distinct words from both documents
            String [] word_seq_text1 = Text1.split("\\s+");
            String [] word_seq_text2 = Text2.split("\\s+");
            Hashtable<String, values> word_freq_vector = new Hashtable<String, values>();
            LinkedList<String> Distinct_words_text_1_2 = new LinkedList<String>();
            //prepare word frequency vector by using Text1
            for(int i=0;i<word_seq_text1.length;i++)
            {
                String tmp_wd = word_seq_text1[i].trim();
                if(tmp_wd.length()>0)
                {
                    if(word_freq_vector.containsKey(tmp_wd))
                    {
                        values vals1 = word_freq_vector.get(tmp_wd);
                        int freq1 = vals1.val1+1;
                        int freq2 = vals1.val2;
                        vals1.Update_VAl(freq1, freq2);
                        word_freq_vector.put(tmp_wd, vals1);
                    }
                    else
                    {
                        values vals1 = new values(1, 0);
                        word_freq_vector.put(tmp_wd, vals1);
                        Distinct_words_text_1_2.add(tmp_wd);
                    }
                }
            }

            //prepare word frequency vector by using Text2
            for(int i=0;i<word_seq_text2.length;i++)
            {
                String tmp_wd = word_seq_text2[i].trim();
                if(tmp_wd.length()>0)
                {
                    if(word_freq_vector.containsKey(tmp_wd))
                    {
                        values vals1 = word_freq_vector.get(tmp_wd);
                        int freq1 = vals1.val1;
                        int freq2 = vals1.val2;
                        if(vals1.val2 < vals1.val1)
                        {
                         freq2 = vals1.val2+1;
                        }             
                        vals1.Update_VAl(freq1, freq2);
                        word_freq_vector.put(tmp_wd, vals1);
                    }
                    else
                    {
                        //values vals1 = new values(0, 1);
                        //word_freq_vector.put(tmp_wd, vals1);
                        //Distinct_words_text_1_2.add(tmp_wd);
                    }
                }
            }

            //calculate the cosine similarity score.
            double VectAB =    0.0000000;
            double VectA_Sq = 0.0000000;
            double VectB_Sq = 0.0000000;

            for(int i=0;i<Distinct_words_text_1_2.size();i++)
            {
                values vals12 = word_freq_vector.get(Distinct_words_text_1_2.get(i));

                double freq1 = (double)vals12.val1;
                double freq2 = (double)vals12.val2;
               // fWriterResult.write(Distinct_words_text_1_2.get(i)+"  #"+ freq1+" #"+freq2);
                //fWriterResult.write("\n");

                VectAB=VectAB+(freq1*freq2);

                VectA_Sq = VectA_Sq + freq1*freq1;
                VectB_Sq = VectB_Sq + freq2*freq2;
            }

            //fWriterResult.write("VectAB "+VectAB+" VectA_Sq "+VectA_Sq+" VectB_Sq "+VectB_Sq);
            //fWriterResult.write("\n");
            if (VectB_Sq ==0)
            {
                sim_score =0.0;
            }
            else
            {
                sim_score = ((VectAB)/(Math.sqrt(VectA_Sq)*Math.sqrt(VectB_Sq)));   
            }
        }
        catch(Exception e)
        {

        }

        return(sim_score);
        //System.out.println("VectAB "+VectAB+" VectA_Sq "+VectA_Sq+" VectB_Sq "+VectB_Sq);
    }

    public double Cosine_Similarity_Score(String Text1, String Text2)
    {
        double sim_score=0.0000000;

     try
     {
      //1. Identify distinct words from both documents
      String [] word_seq_text1 = Text1.split("\\s+");
      String [] word_seq_text2 = Text2.split("\\s+");
      Hashtable<String, values> word_freq_vector = new Hashtable<String, values>();
      LinkedList<String> Distinct_words_text_1_2 = new LinkedList<String>();
      //prepare word frequency vector by using Text1
      for(int i=0;i<word_seq_text1.length;i++)
      {
       String tmp_wd = word_seq_text1[i].trim();
       if(tmp_wd.length()>0)
       {

        if(word_freq_vector.containsKey(tmp_wd))
        {
         values vals1 = word_freq_vector.get(tmp_wd);
         int freq1 = vals1.val1+1;
         int freq2 = vals1.val2;
         vals1.Update_VAl(freq1, freq2);
         word_freq_vector.put(tmp_wd, vals1);
        }
        else
        {
         values vals1 = new values(1, 0);
         word_freq_vector.put(tmp_wd, vals1);
         Distinct_words_text_1_2.add(tmp_wd);
        }
       }
      }

          //prepare word frequency vector by using Text2
          for(int i=0;i<word_seq_text2.length;i++)
          {
           String tmp_wd = word_seq_text2[i].trim();
           if(tmp_wd.length()>0)
           {
            if(word_freq_vector.containsKey(tmp_wd))
            {
             values vals1 = word_freq_vector.get(tmp_wd);
             int freq1 = vals1.val1;
             int freq2 = vals1.val2;             
              freq2 = vals1.val2+1;
                         
             vals1.Update_VAl(freq1, freq2);
             word_freq_vector.put(tmp_wd, vals1);
            }
            else
            {
             values vals1 = new values(0, 1);
             word_freq_vector.put(tmp_wd, vals1);
             Distinct_words_text_1_2.add(tmp_wd);
            }
           }
          }

          //calculate the cosine similarity score.
          double VectAB =    0.0000000;
          double VectA_Sq = 0.0000000;
          double VectB_Sq = 0.0000000;

          for(int i=0;i<Distinct_words_text_1_2.size();i++)
          {
           values vals12 = word_freq_vector.get(Distinct_words_text_1_2.get(i));
           
           double freq1 = (double)vals12.val1;
           double freq2 = (double)vals12.val2;
          // fWriterResult.write(Distinct_words_text_1_2.get(i)+"  #"+ freq1+" #"+freq2);
           //fWriterResult.write("\n");

           VectAB=VectAB+(freq1*freq2);

           VectA_Sq = VectA_Sq + freq1*freq1;
           VectB_Sq = VectB_Sq + freq2*freq2;
           }
            
          //fWriterResult.write("VectAB "+VectAB+" VectA_Sq "+VectA_Sq+" VectB_Sq "+VectB_Sq);
          //fWriterResult.write("\n");
          sim_score = ((VectAB)/(Math.sqrt(VectA_Sq)*Math.sqrt(VectB_Sq)));          

         }
          catch(Exception e)
          {

          }
         return(sim_score);
          //System.out.println("VectAB "+VectAB+" VectA_Sq "+VectA_Sq+" VectB_Sq "+VectB_Sq);
          
     }
      
  
    
     public List<String> removelessthan5(List<String> sentence)
    {
        List<String> tempList = new  ArrayList<>();
        for(int i= 0;i<sentence.size();i++)
        {
            String temp = sentence.get(i);
            String [] listOfWords = temp.split("\\s+");
            if(listOfWords.length>=5)
            {
               tempList.add(sentence.get(i));
            }
        }

       

        return tempList;
        
    }
    
    public String clearSentence(String sentence)
    {
        for(int i =0;i<HtmlCodes.length;i++)
        {
        sentence =sentence.replace(HtmlCodes[i],"");
        }
        String [] listOfWords = sentence.split("\\s+");

        if(listOfWords.length<4)
        {
            sentence="";
        }

        return sentence;
    }
    public final List<String> Normalization (List<String> input)
    {
        List<String> temp = new ArrayList<>();
        List<String> alf = new ArrayList<String>();
        alf.add("أ");
        alf.add("إ");
        alf.add("آ");
        for(int j=0 ;j<input.size();j++)
        {
        for(int i =0;i<alf.size();i++)
        {
        input.set(j, input.get(j).replace(alf.get(i),"ا"));
        }
        input.set(j, input.get(j).replace("ى","ي"));
        input.set(j,input.get(j).trim());
        String replaceMe = Character.toString((char)(65279));
        input.set(j,input.get(j).replace(replaceMe, ""));
        replaceMe = Character.toString((char)(160));
        input.set(j,input.get(j).replace(replaceMe, ""));
        input.set(j,input.get(j).trim());
        temp.add(input.get(j));
        }
        return temp;
    
    }
    
    public List<String> removeStopWords (List<String> ListSentence)
    {
        List<String> temp=new ArrayList<>();
        for(int j=0;j<ListSentence.size();j++)
        {
            String sentence="";
        String [] Token = ListSentence.get(j).split("\\s+");
        for(int i=0 ;i<Token.length;i++)
        {
            if(Token[i].length() ==2)
            {
                sentence+= remove2stopWords(Token[i])+ " ";
            }
            else if(Token[i].length() ==3)
            {
                sentence+= remove3stopWords(Token[i])+ " ";
            }
             else if(Token[i].length() ==4)
            {
                sentence+= remove4stopWords(Token[i])+ " ";
            }
              else if(Token[i].length() ==5)
            {
                sentence+= remove5stopWords(Token[i])+ " ";
            }
            else
            {
                sentence+= Token[i]+ " ";
            }
            
        }
        temp.add(sentence);
        }
    
        return temp;
    }
    
    
    public final String remove2stopWords (String input)
    {
  String stopWord=      "اي\n" +
"كم\n" +
"ما\n" +
"من\n" +
"اي\n" +
"ما\n" +
"من\n" +
"ته\n" +
"تي\n" +
"ثم\n" +
"ذا\n" +
"ذه\n" +
"ذي\n" +
"اي\n" +
"اذ\n" +
"كل\n" +
"مع\n" +
"بس\n" +
"صه\n" +
"صه\n" +
"طق\n" +
"كخ\n" +
"نخ\n" +
"هج\n" +
"وا\n" +
"وي\n" +
"اه\n" +
"اف\n" +
"اف\n" +
"بخ\n" +
"بس\n" +
"حي\n" +
"حي\n" +
"مه\n" +
"ها\n" +
"وا\n" +
"اي\n" +
"ذا\n" +
"ما\n" +
"من\n" +
"اب\n" +
"اخ\n" +
"حم\n" +
"ذو\n" +
"فو\n" +
"لن\n" +
"لو\n" +
"ان\n" +
"ما\n" +
"لا\n" +
"ان\n" +
"ان\n" +
"عل\n" +
"اي\n" +
"كي\n" +
"كل\n" +
"هل\n" +
"قد\n" +
"رب\n" +
"عن\n" +
"في\n" +
"من\n" +
"مذ\n" +
"لم\n" +
"اي\n" +
"ام\n" +
"او\n" +
"بل\n" +
"ثم\n" +
"ان\n" +
"بك\n" +
"به\n" +
"بي\n" +
"لك\n" +
"له\n" +
"لي\n" +
"هم\n" +
"هن\n" +
"هو\n" +
"هي\n" +
"قط\n" +
"اذ\n" +
"ثم\n" +
"هب\n" +
"اض\n" +
"ظل\n" +
"كم";
      stopWord = stopWord.replace('\n', ' ');
      String[] twoWord= stopWord.split("\\s+");
      for(int i=0;i<twoWord.length;i++)
      {
          if(input.equals(twoWord[i]))
          {
             return "";
          }
      }
      return input;
    }
     public final String remove3stopWords (String input)
    {
 String stopWords= "بيد\n" +
"سوي\n" +
"غير\n" +
"متي\n" +
"اني\n" +
"واي\n" +
"فاي\n" +
"اين\n" +
"بكم\n" +
"بما\n" +
"بمن\n" +
"وكم\n" +
"فكم\n" +
"كيف\n" +
"وما\n" +
"فما\n" +
"متي\n" +
"مما\n" +
"ممن\n" +
"ومن\n" +
"فمن\n" +
"اني\n" +
"واي\n" +
"فاي\n" +
"اين\n" +
"وما\n" +
"فما\n" +
"متي\n" +
"ومن\n" +
"فمن\n" +
"تان\n" +
"تلك\n" +
"بته\n" +
"كته\n" +
"لته\n" +
"وته\n" +
"فته\n" +
"بتي\n" +
"كتي\n" +
"لتي\n" +
"وتي\n" +
"فتي\n" +
"تين\n" +
"وثم\n" +
"فثم\n" +
"اثم\n" +
"ثمة\n" +
"بذا\n" +
"كذا\n" +
"لذا\n" +
"وذا\n" +
"فذا\n" +
"ذاك\n" +
"ذان\n" +
"ذلك\n" +
"بذه\n" +
"كذه\n" +
"لذه\n" +
"وذه\n" +
"فذه\n" +
"ذوا\n" +
"بذي\n" +
"كذي\n" +
"لذي\n" +
"وذي\n" +
"فذي\n" +
"اذي\n" +
"ذين\n" +
"هذا\n" +
"هذه\n" +
"هذي\n" +
"هنا\n" +
"باي\n" +
"كاي\n" +
"لاي\n" +
"واي\n" +
"فاي\n" +
"ايي\n" +
"ايك\n" +
"ايه\n" +
"ايي\n" +
"واذ\n" +
"فاذ\n" +
"اذا\n" +
"بعض\n" +
"حسب\n" +
"حيث\n" +
"سوي\n" +
"سوي\n" +
"سوي\n" +
"شبه\n" +
"بكل\n" +
"ككل\n" +
"لكل\n" +
"لكل\n" +
"وكل\n" +
"فكل\n" +
"اكل\n" +
"كلي\n" +
"كلك\n" +
"كله\n" +
"كلي\n" +
"لما\n" +
"مثل\n" +
"لمع\n" +
"ومع\n" +
"فمع\n" +
"امع\n" +
"معي\n" +
"معك\n" +
"معه\n" +
"معي\n" +
"نحو\n" +
"اقل\n" +
"اذا\n" +
"اها\n" +
"حاي\n" +
"طاق\n" +
"عدس\n" +
"واه\n" +
"فاه\n" +
"واف\n" +
"فاف\n" +
"واف\n" +
"فاف\n" +
"اوه\n" +
"ايه\n" +
"وبخ\n" +
"فبخ\n" +
"وبس\n" +
"فبس\n" +
"بله\n" +
"وحي\n" +
"فحي\n" +
"وحي\n" +
"فحي\n" +
"هاك\n" +
"هلم\n" +
"هيا\n" +
"هيت\n" +
"باي\n" +
"كاي\n" +
"لاي\n" +
"واي\n" +
"فاي\n" +
"بذا\n" +
"كذا\n" +
"لذا\n" +
"وذا\n" +
"فذا\n" +
"ذات\n" +
"وما\n" +
"فما\n" +
"بمن\n" +
"كمن\n" +
"لمن\n" +
"ومن\n" +
"فمن\n" +
"امن\n" +
"باب\n" +
"كاب\n" +
"لاب\n" +
"واب\n" +
"فاب\n" +
"ابي\n" +
"ابك\n" +
"ابه\n" +
"ابي\n" +
"باخ\n" +
"كاخ\n" +
"لاخ\n" +
"واخ\n" +
"فاخ\n" +
"اخي\n" +
"اخك\n" +
"اخه\n" +
"اخي\n" +
"بحم\n" +
"كحم\n" +
"لحم\n" +
"وحم\n" +
"فحم\n" +
"حمي\n" +
"حمك\n" +
"حمه\n" +
"حمي\n" +
"وذو\n" +
"فذو\n" +
"بفو\n" +
"كفو\n" +
"لفو\n" +
"وفو\n" +
"ففو\n" +
"فوي\n" +
"فوك\n" +
"فوه\n" +
"فوي\n" +
"ولن\n" +
"فلن\n" +
"الن\n" +
"ولو\n" +
"فلو\n" +
"الو\n" +
"نعم\n" +
"بئس\n" +
"ساء\n" +
"نعم\n" +
"بان\n" +
"كان\n" +
"لان\n" +
"وان\n" +
"فان\n" +
"لات\n" +
"وما\n" +
"فما\n" +
"ولا\n" +
"فلا\n" +
"الا\n" +
"بان\n" +
"كان\n" +
"لان\n" +
"وان\n" +
"فان\n" +
"اني\n" +
"انك\n" +
"انه\n" +
"اني\n" +
"بان\n" +
"كان\n" +
"لان\n" +
"وان\n" +
"فان\n" +
"اان\n" +
"اني\n" +
"انك\n" +
"انه\n" +
"اني\n" +
"وعل\n" +
"فعل\n" +
"كان\n" +
"لعل\n" +
"لكن\n" +
"ليت\n" +
"وكي\n" +
"فكي\n" +
"عين\n" +
"بكل\n" +
"ككل\n" +
"لكل\n" +
"لكل\n" +
"وكل\n" +
"فكل\n" +
"اكل\n" +
"كلي\n" +
"كلك\n" +
"كله\n" +
"كلي\n" +
"كلا\n" +
"نفس\n" +
"الا\n" +
"خلا\n" +
"عدا\n" +
"لكن\n" +
"فيم\n" +
"وهل\n" +
"فهل\n" +
"سوف\n" +
"هلا\n" +
"لقد\n" +
"وقد\n" +
"فقد\n" +
"اما\n" +
"كما\n" +
"لكي\n" +
"الي\n" +
"الي\n" +
"الي\n" +
"ورب\n" +
"فرب\n" +
"علي\n" +
"علي\n" +
"علي\n" +
"لعن\n" +
"وعن\n" +
"فعن\n" +
"اعن\n" +
"عني\n" +
"عنك\n" +
"عنه\n" +
"عني\n" +
"لفي\n" +
"وفي\n" +
"ففي\n" +
"افي\n" +
"فيي\n" +
"فيك\n" +
"فيه\n" +
"فيي\n" +
"لمن\n" +
"ومن\n" +
"فمن\n" +
"امن\n" +
"مني\n" +
"منك\n" +
"منه\n" +
"مني\n" +
"عما\n" +
"حتي\n" +
"منذ\n" +
"ومذ\n" +
"فمذ\n" +
"ولم\n" +
"فلم\n" +
"الم\n" +
"لما\n" +
"اجل\n" +
"اذن\n" +
"واي\n" +
"فاي\n" +
"بلي\n" +
"جلل\n" +
"جير\n" +
"كلا\n" +
"لئن\n" +
"اما\n" +
"الا\n" +
"اما\n" +
"ايا\n" +
"هيا\n" +
"بان\n" +
"كان\n" +
"لان\n" +
"لان\n" +
"وان\n" +
"فان\n" +
"وبك\n" +
"فبك\n" +
"ابك\n" +
"بكم\n" +
"بكن\n" +
"بنا\n" +
"وبه\n" +
"فبه\n" +
"ابه\n" +
"بها\n" +
"وبي\n" +
"فبي\n" +
"ابي\n" +
"ولك\n" +
"فلك\n" +
"الك\n" +
"لكم\n" +
"لكن\n" +
"لنا\n" +
"وله\n" +
"فله\n" +
"اله\n" +
"لها\n" +
"ولي\n" +
"فلي\n" +
"الي\n" +
"انا\n" +
"انت\n" +
"انت\n" +
"نحن\n" +
"بهم\n" +
"كهم\n" +
"لهم\n" +
"لهم\n" +
"وهم\n" +
"فهم\n" +
"اهم\n" +
"هما\n" +
"بهن\n" +
"كهن\n" +
"لهن\n" +
"لهن\n" +
"وهن\n" +
"فهن\n" +
"اهن\n" +
"بهو\n" +
"كهو\n" +
"لهو\n" +
"لهو\n" +
"وهو\n" +
"فهو\n" +
"اهو\n" +
"بهي\n" +
"كهي\n" +
"لهي\n" +
"لهي\n" +
"وهي\n" +
"فهي\n" +
"اهي\n" +
"دون\n" +
"ريث\n" +
"عند\n" +
"عوض\n" +
"قبل\n" +
"وقط\n" +
"فقط\n" +
"لدن\n" +
"لدي\n" +
"لدي\n" +
"لدي\n" +
"لما\n" +
"امد\n" +
"امس\n" +
"اول\n" +
"واذ\n" +
"فاذ\n" +
"بعد\n" +
"حين\n" +
"غدا\n" +
"مرة\n" +
"اين\n" +
"بين\n" +
"تحت\n" +
"وثم\n" +
"فثم\n" +
"خلف\n" +
"ضمن\n" +
"فوق\n" +
"حول\n" +
"اخذ\n" +
"جعل\n" +
"حري\n" +
"شرع\n" +
"طفق\n" +
"عسي\n" +
"علق\n" +
"قام\n" +
"كاد\n" +
"كرب\n" +
"لهب\n" +
"وهب\n" +
"فهب\n" +
"لاض\n" +
"واض\n" +
"فاض\n" +
"بات\n" +
"حار\n" +
"راح\n" +
"رجع\n" +
"صار\n" +
"لظل\n" +
"وظل\n" +
"فظل\n" +
"عاد\n" +
"غدا\n" +
"كان\n" +
"ليس\n" +
"لست\n" +
"لست\n" +
"لست\n" +
"لسن\n" +
"بضع\n" +
"ذيت\n" +
"كاي\n" +
"كذا\n" +
"وكم\n" +
"فكم\n" +
"كيت"; 
       stopWords = stopWords.replace('\n', ' ');
      String[] twoWord= stopWords.split("\\s+");
      for(int i=0;i<twoWord.length;i++)
      {
          if(input.equals(twoWord[i]))
          {
             return "";
          }
      }
      return input;
        
    }
      public final String remove4stopWords (String input)
    {
      String stopWords= "وبيد\n" +
"فبيد\n" +
"وسوي\n" +
"فسوي\n" +
"بغير\n" +
"كغير\n" +
"لغير\n" +
"لغير\n" +
"وغير\n" +
"فغير\n" +
"اغير\n" +
"غيري\n" +
"غيرك\n" +
"غيره\n" +
"غيري\n" +
"ومتي\n" +
"فمتي\n" +
"واني\n" +
"فاني\n" +
"ايان\n" +
"واين\n" +
"فاين\n" +
"وبكم\n" +
"فبكم\n" +
"وبما\n" +
"فبما\n" +
"ابما\n" +
"وبمن\n" +
"فبمن\n" +
"وكيف\n" +
"فكيف\n" +
"ماذا\n" +
"ومتي\n" +
"فمتي\n" +
"ومما\n" +
"فمما\n" +
"امما\n" +
"وممن\n" +
"فممن\n" +
"واني\n" +
"فاني\n" +
"ايان\n" +
"واين\n" +
"فاين\n" +
"ومتي\n" +
"فمتي\n" +
"مهما\n" +
"وتان\n" +
"فتان\n" +
"تانك\n" +
"بتلك\n" +
"كتلك\n" +
"لتلك\n" +
"وتلك\n" +
"فتلك\n" +
"اتلك\n" +
"تلكم\n" +
"وبته\n" +
"فبته\n" +
"وكته\n" +
"فكته\n" +
"ولته\n" +
"فلته\n" +
"وبتي\n" +
"فبتي\n" +
"وكتي\n" +
"فكتي\n" +
"ولتي\n" +
"فلتي\n" +
"بتين\n" +
"كتين\n" +
"لتين\n" +
"وتين\n" +
"فتين\n" +
"تينك\n" +
"اوثم\n" +
"افثم\n" +
"وثمة\n" +
"فثمة\n" +
"اثمة\n" +
"وبذا\n" +
"فبذا\n" +
"وكذا\n" +
"فكذا\n" +
"ولذا\n" +
"فلذا\n" +
"بذاك\n" +
"كذاك\n" +
"لذاك\n" +
"وذاك\n" +
"فذاك\n" +
"اذاك\n" +
"وذان\n" +
"فذان\n" +
"ذانك\n" +
"بذلك\n" +
"كذلك\n" +
"لذلك\n" +
"وذلك\n" +
"فذلك\n" +
"اذلك\n" +
"ذلكم\n" +
"ذلكن\n" +
"وبذه\n" +
"فبذه\n" +
"وكذه\n" +
"فكذه\n" +
"ولذه\n" +
"فلذه\n" +
"وذوا\n" +
"فذوا\n" +
"وبذي\n" +
"فبذي\n" +
"وكذي\n" +
"فكذي\n" +
"ولذي\n" +
"فلذي\n" +
"ابذي\n" +
"اكذي\n" +
"الذي\n" +
"اوذي\n" +
"افذي\n" +
"بذين\n" +
"كذين\n" +
"لذين\n" +
"وذين\n" +
"فذين\n" +
"اذين\n" +
"ذينك\n" +
"كذلك\n" +
"هاته\n" +
"هاتي\n" +
"بهذا\n" +
"كهذا\n" +
"لهذا\n" +
"وهذا\n" +
"فهذا\n" +
"اهذا\n" +
"هذان\n" +
"بهذه\n" +
"كهذه\n" +
"لهذه\n" +
"وهذه\n" +
"فهذه\n" +
"اهذه\n" +
"بهذي\n" +
"كهذي\n" +
"لهذي\n" +
"وهذي\n" +
"فهذي\n" +
"هذين\n" +
"هكذا\n" +
"وهنا\n" +
"فهنا\n" +
"اهنا\n" +
"هناك\n" +
"وباي\n" +
"فباي\n" +
"وكاي\n" +
"فكاي\n" +
"ولاي\n" +
"فلاي\n" +
"ايكم\n" +
"ايكن\n" +
"ايها\n" +
"ايهم\n" +
"ايهن\n" +
"اينا\n" +
"بايي\n" +
"بايك\n" +
"بايه\n" +
"بايي\n" +
"كايي\n" +
"كايك\n" +
"كايه\n" +
"كايي\n" +
"لايي\n" +
"لايك\n" +
"لايه\n" +
"لايي\n" +
"وايي\n" +
"وايك\n" +
"وايه\n" +
"وايي\n" +
"فايي\n" +
"فايك\n" +
"فايه\n" +
"فايي\n" +
"واذا\n" +
"فاذا\n" +
"ببعض\n" +
"كبعض\n" +
"لبعض\n" +
"لبعض\n" +
"وبعض\n" +
"فبعض\n" +
"ابعض\n" +
"بعضي\n" +
"بعضك\n" +
"بعضه\n" +
"بعضي\n" +
"تجاه\n" +
"جميع\n" +
"بحسب\n" +
"كحسب\n" +
"لحسب\n" +
"وحسب\n" +
"فحسب\n" +
"احسب\n" +
"حسبي\n" +
"حسبك\n" +
"حسبه\n" +
"حسبي\n" +
"بحيث\n" +
"كحيث\n" +
"لحيث\n" +
"وحيث\n" +
"فحيث\n" +
"بسوي\n" +
"كسوي\n" +
"لسوي\n" +
"وسوي\n" +
"فسوي\n" +
"اسوي\n" +
"سويك\n" +
"سويه\n" +
"بسوي\n" +
"بسوي\n" +
"كسوي\n" +
"كسوي\n" +
"لسوي\n" +
"لسوي\n" +
"وسوي\n" +
"وسوي\n" +
"فسوي\n" +
"فسوي\n" +
"اسوي\n" +
"اسوي\n" +
"بشبه\n" +
"كشبه\n" +
"لشبه\n" +
"لشبه\n" +
"وشبه\n" +
"فشبه\n" +
"اشبه\n" +
"شبهي\n" +
"شبهك\n" +
"شبهه\n" +
"شبهي\n" +
"وبكل\n" +
"فبكل\n" +
"وككل\n" +
"فككل\n" +
"ولكل\n" +
"فلكل\n" +
"ولكل\n" +
"فلكل\n" +
"ابكل\n" +
"اككل\n" +
"الكل\n" +
"الكل\n" +
"اوكل\n" +
"افكل\n" +
"كلكم\n" +
"كلكن\n" +
"كلها\n" +
"كلهم\n" +
"كلهن\n" +
"كلنا\n" +
"بكلي\n" +
"بكلك\n" +
"بكله\n" +
"بكلي\n" +
"ككلي\n" +
"ككلك\n" +
"ككله\n" +
"ككلي\n" +
"لكلي\n" +
"لكلك\n" +
"لكله\n" +
"لكلي\n" +
"لكلي\n" +
"لكلك\n" +
"لكله\n" +
"لكلي\n" +
"وكلي\n" +
"وكلك\n" +
"وكله\n" +
"وكلي\n" +
"فكلي\n" +
"فكلك\n" +
"فكله\n" +
"فكلي\n" +
"اكلي\n" +
"اكلك\n" +
"اكله\n" +
"اكلي\n" +
"لعمر\n" +
"ولما\n" +
"فلما\n" +
"الما\n" +
"بمثل\n" +
"كمثل\n" +
"لمثل\n" +
"لمثل\n" +
"ومثل\n" +
"فمثل\n" +
"امثل\n" +
"مثلي\n" +
"مثلك\n" +
"مثله\n" +
"مثلي\n" +
"ولمع\n" +
"فلمع\n" +
"المع\n" +
"اومع\n" +
"افمع\n" +
"معكم\n" +
"معكن\n" +
"معها\n" +
"معهم\n" +
"معهن\n" +
"معنا\n" +
"لمعي\n" +
"لمعك\n" +
"لمعه\n" +
"لمعي\n" +
"ومعي\n" +
"ومعك\n" +
"ومعه\n" +
"ومعي\n" +
"فمعي\n" +
"فمعك\n" +
"فمعه\n" +
"فمعي\n" +
"امعي\n" +
"امعك\n" +
"امعه\n" +
"امعي\n" +
"معاذ\n" +
"بنحو\n" +
"كنحو\n" +
"لنحو\n" +
"ونحو\n" +
"فنحو\n" +
"انحو\n" +
"نحوي\n" +
"نحوك\n" +
"نحوه\n" +
"نحوي\n" +
"باقل\n" +
"كاقل\n" +
"لاقل\n" +
"واقل\n" +
"فاقل\n" +
"اقلي\n" +
"اقلك\n" +
"اقله\n" +
"اقلي\n" +
"اكثر\n" +
"واذا\n" +
"فاذا\n" +
"واها\n" +
"امين\n" +
"واوه\n" +
"فاوه\n" +
"اليك\n" +
"اليك\n" +
"اليك\n" +
"وايه\n" +
"فايه\n" +
"بطان\n" +
"وبله\n" +
"فبله\n" +
"حذار\n" +
"دونك\n" +
"شتان\n" +
"عليك\n" +
"هاؤم\n" +
"وهيت\n" +
"فهيت\n" +
"التي\n" +
"للتي\n" +
"الذي\n" +
"للذي\n" +
"وباي\n" +
"فباي\n" +
"وكاي\n" +
"فكاي\n" +
"ولاي\n" +
"فلاي\n" +
"وبذا\n" +
"فبذا\n" +
"وكذا\n" +
"فكذا\n" +
"ولذا\n" +
"فلذا\n" +
"بذات\n" +
"كذات\n" +
"لذات\n" +
"وذات\n" +
"فذات\n" +
"وبمن\n" +
"فبمن\n" +
"وكمن\n" +
"فكمن\n" +
"ولمن\n" +
"فلمن\n" +
"ابمن\n" +
"اكمن\n" +
"المن\n" +
"اومن\n" +
"افمن\n" +
"وباب\n" +
"فباب\n" +
"وكاب\n" +
"فكاب\n" +
"ولاب\n" +
"فلاب\n" +
"ابكم\n" +
"ابكن\n" +
"ابها\n" +
"ابهم\n" +
"ابهن\n" +
"ابنا\n" +
"الاب\n" +
"بابي\n" +
"بابك\n" +
"بابه\n" +
"بابي\n" +
"كابي\n" +
"كابك\n" +
"كابه\n" +
"كابي\n" +
"لابي\n" +
"لابك\n" +
"لابه\n" +
"لابي\n" +
"وابي\n" +
"وابك\n" +
"وابه\n" +
"وابي\n" +
"فابي\n" +
"فابك\n" +
"فابه\n" +
"فابي\n" +
"وباخ\n" +
"فباخ\n" +
"وكاخ\n" +
"فكاخ\n" +
"ولاخ\n" +
"فلاخ\n" +
"اخكم\n" +
"اخكن\n" +
"اخها\n" +
"اخهم\n" +
"اخهن\n" +
"اخنا\n" +
"الاخ\n" +
"باخي\n" +
"باخك\n" +
"باخه\n" +
"باخي\n" +
"كاخي\n" +
"كاخك\n" +
"كاخه\n" +
"كاخي\n" +
"لاخي\n" +
"لاخك\n" +
"لاخه\n" +
"لاخي\n" +
"واخي\n" +
"واخك\n" +
"واخه\n" +
"واخي\n" +
"فاخي\n" +
"فاخك\n" +
"فاخه\n" +
"فاخي\n" +
"وبحم\n" +
"فبحم\n" +
"وكحم\n" +
"فكحم\n" +
"ولحم\n" +
"فلحم\n" +
"حمكم\n" +
"حمكن\n" +
"حمها\n" +
"حمهم\n" +
"حمهن\n" +
"حمنا\n" +
"الحم\n" +
"بحمي\n" +
"بحمك\n" +
"بحمه\n" +
"بحمي\n" +
"كحمي\n" +
"كحمك\n" +
"كحمه\n" +
"كحمي\n" +
"لحمي\n" +
"لحمك\n" +
"لحمه\n" +
"لحمي\n" +
"وحمي\n" +
"وحمك\n" +
"وحمه\n" +
"وحمي\n" +
"فحمي\n" +
"فحمك\n" +
"فحمه\n" +
"فحمي\n" +
"وبفو\n" +
"فبفو\n" +
"وكفو\n" +
"فكفو\n" +
"ولفو\n" +
"فلفو\n" +
"فوكم\n" +
"فوكن\n" +
"فوها\n" +
"فوهم\n" +
"فوهن\n" +
"فونا\n" +
"الفو\n" +
"بفوي\n" +
"بفوك\n" +
"بفوه\n" +
"بفوي\n" +
"كفوي\n" +
"كفوك\n" +
"كفوه\n" +
"كفوي\n" +
"لفوي\n" +
"لفوك\n" +
"لفوه\n" +
"لفوي\n" +
"وفوي\n" +
"وفوك\n" +
"وفوه\n" +
"وفوي\n" +
"ففوي\n" +
"ففوك\n" +
"ففوه\n" +
"ففوي\n" +
"اولن\n" +
"افلن\n" +
"اولو\n" +
"افلو\n" +
"لولا\n" +
"لوما\n" +
"ونعم\n" +
"فنعم\n" +
"وبئس\n" +
"فبئس\n" +
"حبذا\n" +
"وساء\n" +
"فساء\n" +
"ونعم\n" +
"فنعم\n" +
"نعما\n" +
"وبان\n" +
"فبان\n" +
"وكان\n" +
"فكان\n" +
"ولان\n" +
"فلان\n" +
"ولات\n" +
"فلات\n" +
"اولا\n" +
"افلا\n" +
"وبان\n" +
"فبان\n" +
"وكان\n" +
"فكان\n" +
"ولان\n" +
"فلان\n" +
"انكم\n" +
"انكن\n" +
"انها\n" +
"انهم\n" +
"انهن\n" +
"اننا\n" +
"باني\n" +
"بانك\n" +
"بانه\n" +
"باني\n" +
"كاني\n" +
"كانك\n" +
"كانه\n" +
"كاني\n" +
"لاني\n" +
"لانك\n" +
"لانه\n" +
"لاني\n" +
"واني\n" +
"وانك\n" +
"وانه\n" +
"واني\n" +
"فاني\n" +
"فانك\n" +
"فانه\n" +
"فاني\n" +
"وبان\n" +
"فبان\n" +
"وكان\n" +
"فكان\n" +
"ولان\n" +
"فلان\n" +
"ابان\n" +
"اكان\n" +
"الان\n" +
"اوان\n" +
"افان\n" +
"انكم\n" +
"انكن\n" +
"انها\n" +
"انهم\n" +
"انهن\n" +
"اننا\n" +
"باني\n" +
"بانك\n" +
"بانه\n" +
"باني\n" +
"كاني\n" +
"كانك\n" +
"كانه\n" +
"كاني\n" +
"لاني\n" +
"لانك\n" +
"لانه\n" +
"لاني\n" +
"واني\n" +
"وانك\n" +
"وانه\n" +
"واني\n" +
"فاني\n" +
"فانك\n" +
"فانه\n" +
"فاني\n" +
"ااني\n" +
"اانك\n" +
"اانه\n" +
"ااني\n" +
"لكان\n" +
"وكان\n" +
"فكان\n" +
"اكان\n" +
"كاني\n" +
"كانك\n" +
"كانه\n" +
"كاني\n" +
"ولعل\n" +
"فلعل\n" +
"العل\n" +
"لعلي\n" +
"لعلك\n" +
"لعله\n" +
"لعلي\n" +
"ولكن\n" +
"فلكن\n" +
"لكني\n" +
"لكنك\n" +
"لكنه\n" +
"لكني\n" +
"وليت\n" +
"فليت\n" +
"ليتي\n" +
"ليتك\n" +
"ليته\n" +
"ليتي\n" +
"اجمع\n" +
"جميع\n" +
"عامة\n" +
"بعين\n" +
"كعين\n" +
"لعين\n" +
"لعين\n" +
"وعين\n" +
"فعين\n" +
"اعين\n" +
"عيني\n" +
"عينك\n" +
"عينه\n" +
"عيني\n" +
"وبكل\n" +
"فبكل\n" +
"وككل\n" +
"فككل\n" +
"ولكل\n" +
"فلكل\n" +
"ولكل\n" +
"فلكل\n" +
"ابكل\n" +
"اككل\n" +
"الكل\n" +
"الكل\n" +
"اوكل\n" +
"افكل\n" +
"كلكم\n" +
"كلكن\n" +
"كلها\n" +
"كلهم\n" +
"كلهن\n" +
"كلنا\n" +
"بكلي\n" +
"بكلك\n" +
"بكله\n" +
"بكلي\n" +
"ككلي\n" +
"ككلك\n" +
"ككله\n" +
"ككلي\n" +
"لكلي\n" +
"لكلك\n" +
"لكله\n" +
"لكلي\n" +
"لكلي\n" +
"لكلك\n" +
"لكله\n" +
"لكلي\n" +
"وكلي\n" +
"وكلك\n" +
"وكله\n" +
"وكلي\n" +
"فكلي\n" +
"فكلك\n" +
"فكله\n" +
"فكلي\n" +
"اكلي\n" +
"اكلك\n" +
"اكله\n" +
"اكلي\n" +
"وكلا\n" +
"فكلا\n" +
"اكلا\n" +
"كلتا\n" +
"بنفس\n" +
"كنفس\n" +
"لنفس\n" +
"لنفس\n" +
"ونفس\n" +
"فنفس\n" +
"انفس\n" +
"نفسي\n" +
"نفسك\n" +
"نفسه\n" +
"نفسي\n" +
"والا\n" +
"فالا\n" +
"حاشا\n" +
"وخلا\n" +
"فخلا\n" +
"خلاي\n" +
"خلاك\n" +
"خلاه\n" +
"خلاي\n" +
"وعدا\n" +
"فعدا\n" +
"عداي\n" +
"عداك\n" +
"عداه\n" +
"عداي\n" +
"ولكن\n" +
"فلكن\n" +
"لكني\n" +
"لكنك\n" +
"لكنه\n" +
"لكني\n" +
"وفيم\n" +
"ففيم\n" +
"فيما\n" +
"لسوف\n" +
"وسوف\n" +
"فسوف\n" +
"وهلا\n" +
"فهلا\n" +
"ولقد\n" +
"فلقد\n" +
"واما\n" +
"فاما\n" +
"وكما\n" +
"فكما\n" +
"ولكي\n" +
"فلكي\n" +
"لالي\n" +
"والي\n" +
"فالي\n" +
"االي\n" +
"اليك\n" +
"اليه\n" +
"لالي\n" +
"لالي\n" +
"والي\n" +
"والي\n" +
"فالي\n" +
"فالي\n" +
"االي\n" +
"االي\n" +
"لعلي\n" +
"وعلي\n" +
"فعلي\n" +
"اعلي\n" +
"عليك\n" +
"عليه\n" +
"لعلي\n" +
"لعلي\n" +
"وعلي\n" +
"وعلي\n" +
"فعلي\n" +
"فعلي\n" +
"اعلي\n" +
"اعلي\n" +
"ولعن\n" +
"فلعن\n" +
"العن\n" +
"اوعن\n" +
"افعن\n" +
"عنكم\n" +
"عنكن\n" +
"عنها\n" +
"عنهم\n" +
"عنهن\n" +
"عننا\n" +
"لعني\n" +
"لعنك\n" +
"لعنه\n" +
"لعني\n" +
"وعني\n" +
"وعنك\n" +
"وعنه\n" +
"وعني\n" +
"فعني\n" +
"فعنك\n" +
"فعنه\n" +
"فعني\n" +
"اعني\n" +
"اعنك\n" +
"اعنه\n" +
"اعني\n" +
"ولفي\n" +
"فلفي\n" +
"الفي\n" +
"اوفي\n" +
"اففي\n" +
"فيكم\n" +
"فيكن\n" +
"فيها\n" +
"فيهم\n" +
"فيهن\n" +
"فينا\n" +
"لفيي\n" +
"لفيك\n" +
"لفيه\n" +
"لفيي\n" +
"وفيي\n" +
"وفيك\n" +
"وفيه\n" +
"وفيي\n" +
"ففيي\n" +
"ففيك\n" +
"ففيه\n" +
"ففيي\n" +
"افيي\n" +
"افيك\n" +
"افيه\n" +
"افيي\n" +
"ولمن\n" +
"فلمن\n" +
"المن\n" +
"اومن\n" +
"افمن\n" +
"منكم\n" +
"منكن\n" +
"منها\n" +
"منهم\n" +
"منهن\n" +
"مننا\n" +
"لمني\n" +
"لمنك\n" +
"لمنه\n" +
"لمني\n" +
"ومني\n" +
"ومنك\n" +
"ومنه\n" +
"ومني\n" +
"فمني\n" +
"فمنك\n" +
"فمنه\n" +
"فمني\n" +
"امني\n" +
"امنك\n" +
"امنه\n" +
"امني\n" +
"وعما\n" +
"فعما\n" +
"وحتي\n" +
"فحتي\n" +
"ومنذ\n" +
"فمنذ\n" +
"اولم\n" +
"افلم\n" +
"ولما\n" +
"فلما\n" +
"الما\n" +
"واذن\n" +
"فاذن\n" +
"وجير\n" +
"فجير\n" +
"وكلا\n" +
"فكلا\n" +
"اذما\n" +
"ولئن\n" +
"فلئن\n" +
"واما\n" +
"فاما\n" +
"والا\n" +
"فالا\n" +
"واما\n" +
"فاما\n" +
"وايا\n" +
"فايا\n" +
"وهيا\n" +
"فهيا\n" +
"وبان\n" +
"فبان\n" +
"وكان\n" +
"فكان\n" +
"ولان\n" +
"فلان\n" +
"ولان\n" +
"فلان\n" +
"اوبك\n" +
"افبك\n" +
"وبكم\n" +
"فبكم\n" +
"ابكم\n" +
"بكما\n" +
"وبكن\n" +
"فبكن\n" +
"ابكن\n" +
"وبنا\n" +
"فبنا\n" +
"ابنا\n" +
"اوبه\n" +
"افبه\n" +
"وبها\n" +
"فبها\n" +
"ابها\n" +
"اوبي\n" +
"افبي\n" +
"اولك\n" +
"افلك\n" +
"ولكم\n" +
"فلكم\n" +
"الكم\n" +
"لكما\n" +
"ولكن\n" +
"فلكن\n" +
"الكن\n" +
"ولنا\n" +
"فلنا\n" +
"النا\n" +
"اوله\n" +
"افله\n" +
"ولها\n" +
"فلها\n" +
"الها\n" +
"اولي\n" +
"افلي\n" +
"لانا\n" +
"وانا\n" +
"فانا\n" +
"اانا\n" +
"لانت\n" +
"وانت\n" +
"فانت\n" +
"اانت\n" +
"لانت\n" +
"وانت\n" +
"فانت\n" +
"اانت\n" +
"انتم\n" +
"انتن\n" +
"لنحن\n" +
"ونحن\n" +
"فنحن\n" +
"انحن\n" +
"وبهم\n" +
"فبهم\n" +
"وكهم\n" +
"فكهم\n" +
"ولهم\n" +
"فلهم\n" +
"ولهم\n" +
"فلهم\n" +
"ابهم\n" +
"اكهم\n" +
"الهم\n" +
"الهم\n" +
"اوهم\n" +
"افهم\n" +
"بهما\n" +
"كهما\n" +
"لهما\n" +
"لهما\n" +
"وهما\n" +
"فهما\n" +
"اهما\n" +
"وبهن\n" +
"فبهن\n" +
"وكهن\n" +
"فكهن\n" +
"ولهن\n" +
"فلهن\n" +
"ولهن\n" +
"فلهن\n" +
"ابهن\n" +
"اكهن\n" +
"الهن\n" +
"الهن\n" +
"اوهن\n" +
"افهن\n" +
"وبهو\n" +
"فبهو\n" +
"وكهو\n" +
"فكهو\n" +
"ولهو\n" +
"فلهو\n" +
"ولهو\n" +
"فلهو\n" +
"ابهو\n" +
"اكهو\n" +
"الهو\n" +
"الهو\n" +
"اوهو\n" +
"افهو\n" +
"وبهي\n" +
"فبهي\n" +
"وكهي\n" +
"فكهي\n" +
"ولهي\n" +
"فلهي\n" +
"ولهي\n" +
"فلهي\n" +
"ابهي\n" +
"اكهي\n" +
"الهي\n" +
"الهي\n" +
"اوهي\n" +
"افهي\n" +
"اياك\n" +
"اياك\n" +
"اياه\n" +
"اياي\n" +
"ودون\n" +
"فدون\n" +
"وريث\n" +
"فريث\n" +
"وعند\n" +
"فعند\n" +
"عندي\n" +
"عندك\n" +
"عنده\n" +
"عندي\n" +
"وعوض\n" +
"فعوض\n" +
"وقبل\n" +
"فقبل\n" +
"كلما\n" +
"ولدن\n" +
"فلدن\n" +
"لدني\n" +
"لدنك\n" +
"لدنه\n" +
"لدني\n" +
"ولدي\n" +
"فلدي\n" +
"الدي\n" +
"لديك\n" +
"لديه\n" +
"ولدي\n" +
"ولدي\n" +
"فلدي\n" +
"فلدي\n" +
"الدي\n" +
"الدي\n" +
"ولما\n" +
"فلما\n" +
"الما\n" +
"الان\n" +
"اناء\n" +
"انفا\n" +
"ابدا\n" +
"اصلا\n" +
"وامد\n" +
"فامد\n" +
"وامس\n" +
"فامس\n" +
"واول\n" +
"فاول\n" +
"ايان\n" +
"وبعد\n" +
"فبعد\n" +
"بعدي\n" +
"بعدك\n" +
"بعده\n" +
"بعدي\n" +
"تارة\n" +
"وحين\n" +
"فحين\n" +
"حيني\n" +
"حينك\n" +
"حينه\n" +
"حيني\n" +
"صباح\n" +
"ضحوة\n" +
"وغدا\n" +
"فغدا\n" +
"غداة\n" +
"ومرة\n" +
"فمرة\n" +
"مساء\n" +
"خلال\n" +
"امام\n" +
"واين\n" +
"فاين\n" +
"ازاء\n" +
"وبين\n" +
"فبين\n" +
"بيني\n" +
"بينك\n" +
"بينه\n" +
"بيني\n" +
"لتحت\n" +
"وتحت\n" +
"فتحت\n" +
"اتحت\n" +
"تحتي\n" +
"تحتك\n" +
"تحته\n" +
"تحتي\n" +
"لخلف\n" +
"وخلف\n" +
"فخلف\n" +
"اخلف\n" +
"خلفي\n" +
"خلفك\n" +
"خلفه\n" +
"خلفي\n" +
"شمال\n" +
"وضمن\n" +
"فضمن\n" +
"ضمني\n" +
"ضمنك\n" +
"ضمنه\n" +
"ضمني\n" +
"لفوق\n" +
"وفوق\n" +
"ففوق\n" +
"افوق\n" +
"فوقي\n" +
"فوقك\n" +
"فوقه\n" +
"فوقي\n" +
"يمين\n" +
"وحول\n" +
"فحول\n" +
"حولي\n" +
"حولك\n" +
"حوله\n" +
"حولي\n" +
"قلما\n" +
"لاخذ\n" +
"واخذ\n" +
"فاخذ\n" +
"اقبل\n" +
"انشا\n" +
"اوشك\n" +
"لجعل\n" +
"وجعل\n" +
"فجعل\n" +
"لحري\n" +
"وحري\n" +
"فحري\n" +
"لشرع\n" +
"وشرع\n" +
"فشرع\n" +
"لطفق\n" +
"وطفق\n" +
"فطفق\n" +
"لعسي\n" +
"وعسي\n" +
"فعسي\n" +
"لعلق\n" +
"وعلق\n" +
"فعلق\n" +
"لقام\n" +
"وقام\n" +
"فقام\n" +
"لكاد\n" +
"وكاد\n" +
"فكاد\n" +
"لكرب\n" +
"وكرب\n" +
"فكرب\n" +
"ولهب\n" +
"فلهب\n" +
"انما\n" +
"ارتد\n" +
"ولاض\n" +
"فلاض\n" +
"اصبح\n" +
"اضحي\n" +
"امسي\n" +
"لبات\n" +
"وبات\n" +
"فبات\n" +
"تبدل\n" +
"تحول\n" +
"لحار\n" +
"وحار\n" +
"فحار\n" +
"لراح\n" +
"وراح\n" +
"فراح\n" +
"لرجع\n" +
"ورجع\n" +
"فرجع\n" +
"لصار\n" +
"وصار\n" +
"فصار\n" +
"ولظل\n" +
"فلظل\n" +
"لعاد\n" +
"وعاد\n" +
"فعاد\n" +
"لغدا\n" +
"وغدا\n" +
"فغدا\n" +
"لكان\n" +
"وكان\n" +
"فكان\n" +
"وليس\n" +
"فليس\n" +
"اليس\n" +
"ولست\n" +
"فلست\n" +
"الست\n" +
"لسنا\n" +
"ولست\n" +
"فلست\n" +
"الست\n" +
"ولست\n" +
"فلست\n" +
"الست\n" +
"لستم\n" +
"لستن\n" +
"ليست\n" +
"ليسا\n" +
"ولسن\n" +
"فلسن\n" +
"السن\n" +
"وبضع\n" +
"فبضع\n" +
"ابضع\n" +
"بضعي\n" +
"بضعك\n" +
"بضعه\n" +
"بضعي\n" +
"وذيت\n" +
"فذيت\n" +
"فلان\n" +
"وكاي\n" +
"فكاي\n" +
"كاين\n" +
"كاين\n" +
"وكذا\n" +
"فكذا\n" +
"اكذا\n" +
"وكيت\n" +
"فكيت";
       stopWords = stopWords.replace('\n', ' ');
      String[] twoWord= stopWords.split("\\s+");
      for(int i=0;i<twoWord.length;i++)
      {
          if(input.equals(twoWord[i]))
          {
             return "";
          }
      }
      return input;
        
    }
       public final String remove5stopWords (String input)
    {
        String stopWords = "وبغير\n" +
"فبغير\n" +
"وكغير\n" +
"فكغير\n" +
"ولغير\n" +
"فلغير\n" +
"ولغير\n" +
"فلغير\n" +
"ابغير\n" +
"اكغير\n" +
"الغير\n" +
"الغير\n" +
"اوغير\n" +
"افغير\n" +
"غيركم\n" +
"غيركن\n" +
"غيرها\n" +
"غيرهم\n" +
"غيرهن\n" +
"غيرنا\n" +
"بغيري\n" +
"بغيرك\n" +
"بغيره\n" +
"بغيري\n" +
"كغيري\n" +
"كغيرك\n" +
"كغيره\n" +
"كغيري\n" +
"لغيري\n" +
"لغيرك\n" +
"لغيره\n" +
"لغيري\n" +
"لغيري\n" +
"لغيرك\n" +
"لغيره\n" +
"لغيري\n" +
"وغيري\n" +
"وغيرك\n" +
"وغيره\n" +
"وغيري\n" +
"فغيري\n" +
"فغيرك\n" +
"فغيره\n" +
"فغيري\n" +
"اغيري\n" +
"اغيرك\n" +
"اغيره\n" +
"اغيري\n" +
"وايان\n" +
"فايان\n" +
"اوبما\n" +
"افبما\n" +
"بماذا\n" +
"وماذا\n" +
"فماذا\n" +
"اماذا\n" +
"اومما\n" +
"افمما\n" +
"وايان\n" +
"فايان\n" +
"اينما\n" +
"حيثما\n" +
"كيفما\n" +
"ومهما\n" +
"فمهما\n" +
"امهما\n" +
"اولئك\n" +
"اولاء\n" +
"بتانك\n" +
"كتانك\n" +
"لتانك\n" +
"وتانك\n" +
"فتانك\n" +
"وبتلك\n" +
"فبتلك\n" +
"وكتلك\n" +
"فكتلك\n" +
"ولتلك\n" +
"فلتلك\n" +
"ابتلك\n" +
"اكتلك\n" +
"التلك\n" +
"اوتلك\n" +
"افتلك\n" +
"بتلكم\n" +
"كتلكم\n" +
"لتلكم\n" +
"وتلكم\n" +
"فتلكم\n" +
"اتلكم\n" +
"تلكما\n" +
"وبتين\n" +
"فبتين\n" +
"وكتين\n" +
"فكتين\n" +
"ولتين\n" +
"فلتين\n" +
"بتينك\n" +
"كتينك\n" +
"لتينك\n" +
"وتينك\n" +
"فتينك\n" +
"اتينك\n" +
"اوثمة\n" +
"افثمة\n" +
"وبذاك\n" +
"فبذاك\n" +
"وكذاك\n" +
"فكذاك\n" +
"ولذاك\n" +
"فلذاك\n" +
"ابذاك\n" +
"اكذاك\n" +
"الذاك\n" +
"اوذاك\n" +
"افذاك\n" +
"بذانك\n" +
"كذانك\n" +
"لذانك\n" +
"وذانك\n" +
"فذانك\n" +
"اذانك\n" +
"وبذلك\n" +
"فبذلك\n" +
"وكذلك\n" +
"فكذلك\n" +
"ولذلك\n" +
"فلذلك\n" +
"ابذلك\n" +
"اكذلك\n" +
"الذلك\n" +
"اوذلك\n" +
"افذلك\n" +
"بذلكم\n" +
"كذلكم\n" +
"لذلكم\n" +
"وذلكم\n" +
"فذلكم\n" +
"اذلكم\n" +
"ذلكما\n" +
"بذلكن\n" +
"كذلكن\n" +
"لذلكن\n" +
"وذلكن\n" +
"فذلكن\n" +
"اذلكن\n" +
"ذواتا\n" +
"ذواتي\n" +
"اوبذي\n" +
"افبذي\n" +
"اوكذي\n" +
"افكذي\n" +
"اولذي\n" +
"افلذي\n" +
"وبذين\n" +
"فبذين\n" +
"وكذين\n" +
"فكذين\n" +
"ولذين\n" +
"فلذين\n" +
"ابذين\n" +
"اكذين\n" +
"الذين\n" +
"اوذين\n" +
"افذين\n" +
"بذينك\n" +
"كذينك\n" +
"لذينك\n" +
"وذينك\n" +
"فذينك\n" +
"اذينك\n" +
"وكذلك\n" +
"فكذلك\n" +
"اكذلك\n" +
"هؤلاء\n" +
"هاتان\n" +
"بهاته\n" +
"كهاته\n" +
"لهاته\n" +
"وهاته\n" +
"فهاته\n" +
"اهاته\n" +
"بهاتي\n" +
"كهاتي\n" +
"لهاتي\n" +
"وهاتي\n" +
"فهاتي\n" +
"اهاتي\n" +
"هاتين\n" +
"هاهنا\n" +
"وبهذا\n" +
"فبهذا\n" +
"وكهذا\n" +
"فكهذا\n" +
"ولهذا\n" +
"فلهذا\n" +
"ابهذا\n" +
"اكهذا\n" +
"الهذا\n" +
"اوهذا\n" +
"افهذا\n" +
"وهذان\n" +
"فهذان\n" +
"اهذان\n" +
"وبهذه\n" +
"فبهذه\n" +
"وكهذه\n" +
"فكهذه\n" +
"ولهذه\n" +
"فلهذه\n" +
"ابهذه\n" +
"اكهذه\n" +
"الهذه\n" +
"اوهذه\n" +
"افهذه\n" +
"وبهذي\n" +
"فبهذي\n" +
"وكهذي\n" +
"فكهذي\n" +
"ولهذي\n" +
"فلهذي\n" +
"بهذين\n" +
"كهذين\n" +
"لهذين\n" +
"وهذين\n" +
"فهذين\n" +
"وهكذا\n" +
"فهكذا\n" +
"اهكذا\n" +
"اوهنا\n" +
"افهنا\n" +
"بهناك\n" +
"كهناك\n" +
"لهناك\n" +
"وهناك\n" +
"فهناك\n" +
"اهناك\n" +
"هنالك\n" +
"ايكما\n" +
"ايهما\n" +
"بايكم\n" +
"بايكن\n" +
"بايها\n" +
"بايهم\n" +
"بايهن\n" +
"باينا\n" +
"كايكم\n" +
"كايكن\n" +
"كايها\n" +
"كايهم\n" +
"كايهن\n" +
"كاينا\n" +
"لايكم\n" +
"لايكن\n" +
"لايها\n" +
"لايهم\n" +
"لايهن\n" +
"لاينا\n" +
"وايكم\n" +
"وايكن\n" +
"وايها\n" +
"وايهم\n" +
"وايهن\n" +
"واينا\n" +
"فايكم\n" +
"فايكن\n" +
"فايها\n" +
"فايهم\n" +
"فايهن\n" +
"فاينا\n" +
"وبايي\n" +
"وبايك\n" +
"وبايه\n" +
"وبايي\n" +
"فبايي\n" +
"فبايك\n" +
"فبايه\n" +
"فبايي\n" +
"وكايي\n" +
"وكايك\n" +
"وكايه\n" +
"وكايي\n" +
"فكايي\n" +
"فكايك\n" +
"فكايه\n" +
"فكايي\n" +
"ولايي\n" +
"ولايك\n" +
"ولايه\n" +
"ولايي\n" +
"فلايي\n" +
"فلايك\n" +
"فلايه\n" +
"فلايي\n" +
"وببعض\n" +
"فببعض\n" +
"وكبعض\n" +
"فكبعض\n" +
"ولبعض\n" +
"فلبعض\n" +
"ولبعض\n" +
"فلبعض\n" +
"اببعض\n" +
"اكبعض\n" +
"البعض\n" +
"البعض\n" +
"اوبعض\n" +
"افبعض\n" +
"بعضكم\n" +
"بعضكن\n" +
"بعضها\n" +
"بعضهم\n" +
"بعضهن\n" +
"بعضنا\n" +
"ببعضي\n" +
"ببعضك\n" +
"ببعضه\n" +
"ببعضي\n" +
"كبعضي\n" +
"كبعضك\n" +
"كبعضه\n" +
"كبعضي\n" +
"لبعضي\n" +
"لبعضك\n" +
"لبعضه\n" +
"لبعضي\n" +
"لبعضي\n" +
"لبعضك\n" +
"لبعضه\n" +
"لبعضي\n" +
"وبعضي\n" +
"وبعضك\n" +
"وبعضه\n" +
"وبعضي\n" +
"فبعضي\n" +
"فبعضك\n" +
"فبعضه\n" +
"فبعضي\n" +
"ابعضي\n" +
"ابعضك\n" +
"ابعضه\n" +
"ابعضي\n" +
"بتجاه\n" +
"كتجاه\n" +
"لتجاه\n" +
"وتجاه\n" +
"فتجاه\n" +
"تجاهي\n" +
"تجاهك\n" +
"تجاهه\n" +
"تجاهي\n" +
"تلقاء\n" +
"بجميع\n" +
"كجميع\n" +
"لجميع\n" +
"لجميع\n" +
"وجميع\n" +
"فجميع\n" +
"اجميع\n" +
"جميعي\n" +
"جميعك\n" +
"جميعه\n" +
"جميعي\n" +
"وبحسب\n" +
"فبحسب\n" +
"وكحسب\n" +
"فكحسب\n" +
"ولحسب\n" +
"فلحسب\n" +
"ابحسب\n" +
"اكحسب\n" +
"الحسب\n" +
"اوحسب\n" +
"افحسب\n" +
"حسبكم\n" +
"حسبكن\n" +
"حسبها\n" +
"حسبهم\n" +
"حسبهن\n" +
"حسبنا\n" +
"بحسبي\n" +
"بحسبك\n" +
"بحسبه\n" +
"بحسبي\n" +
"كحسبي\n" +
"كحسبك\n" +
"كحسبه\n" +
"كحسبي\n" +
"لحسبي\n" +
"لحسبك\n" +
"لحسبه\n" +
"لحسبي\n" +
"وحسبي\n" +
"وحسبك\n" +
"وحسبه\n" +
"وحسبي\n" +
"فحسبي\n" +
"فحسبك\n" +
"فحسبه\n" +
"فحسبي\n" +
"احسبي\n" +
"احسبك\n" +
"احسبه\n" +
"احسبي\n" +
"وبحيث\n" +
"فبحيث\n" +
"وكحيث\n" +
"فكحيث\n" +
"ولحيث\n" +
"فلحيث\n" +
"سبحان\n" +
"وبسوي\n" +
"فبسوي\n" +
"وكسوي\n" +
"فكسوي\n" +
"ولسوي\n" +
"فلسوي\n" +
"ابسوي\n" +
"اكسوي\n" +
"السوي\n" +
"اوسوي\n" +
"افسوي\n" +
"سويكم\n" +
"سويكن\n" +
"سويها\n" +
"سويهم\n" +
"سويهن\n" +
"سوينا\n" +
"بسويك\n" +
"بسويه\n" +
"كسويك\n" +
"كسويه\n" +
"لسويك\n" +
"لسويه\n" +
"وسويك\n" +
"وسويه\n" +
"فسويك\n" +
"فسويه\n" +
"وبسوي\n" +
"وبسوي\n" +
"فبسوي\n" +
"فبسوي\n" +
"وكسوي\n" +
"وكسوي\n" +
"فكسوي\n" +
"فكسوي\n" +
"ولسوي\n" +
"ولسوي\n" +
"فلسوي\n" +
"فلسوي\n" +
"اسويك\n" +
"اسويه\n" +
"ابسوي\n" +
"ابسوي\n" +
"اكسوي\n" +
"اكسوي\n" +
"السوي\n" +
"السوي\n" +
"اوسوي\n" +
"اوسوي\n" +
"افسوي\n" +
"افسوي\n" +
"وبشبه\n" +
"فبشبه\n" +
"وكشبه\n" +
"فكشبه\n" +
"ولشبه\n" +
"فلشبه\n" +
"ولشبه\n" +
"فلشبه\n" +
"ابشبه\n" +
"اكشبه\n" +
"الشبه\n" +
"الشبه\n" +
"اوشبه\n" +
"افشبه\n" +
"شبهكم\n" +
"شبهكن\n" +
"شبهها\n" +
"شبههم\n" +
"شبههن\n" +
"شبهنا\n" +
"بشبهي\n" +
"بشبهك\n" +
"بشبهه\n" +
"بشبهي\n" +
"كشبهي\n" +
"كشبهك\n" +
"كشبهه\n" +
"كشبهي\n" +
"لشبهي\n" +
"لشبهك\n" +
"لشبهه\n" +
"لشبهي\n" +
"لشبهي\n" +
"لشبهك\n" +
"لشبهه\n" +
"لشبهي\n" +
"وشبهي\n" +
"وشبهك\n" +
"وشبهه\n" +
"وشبهي\n" +
"فشبهي\n" +
"فشبهك\n" +
"فشبهه\n" +
"فشبهي\n" +
"اشبهي\n" +
"اشبهك\n" +
"اشبهه\n" +
"اشبهي\n" +
"اوبكل\n" +
"افبكل\n" +
"اوككل\n" +
"افككل\n" +
"اولكل\n" +
"افلكل\n" +
"اولكل\n" +
"افلكل\n" +
"كلكما\n" +
"كلهما\n" +
"بكلكم\n" +
"بكلكن\n" +
"بكلها\n" +
"بكلهم\n" +
"بكلهن\n" +
"بكلنا\n" +
"ككلكم\n" +
"ككلكن\n" +
"ككلها\n" +
"ككلهم\n" +
"ككلهن\n" +
"ككلنا\n" +
"لكلكم\n" +
"لكلكن\n" +
"لكلها\n" +
"لكلهم\n" +
"لكلهن\n" +
"لكلنا\n" +
"لكلكم\n" +
"لكلكن\n" +
"لكلها\n" +
"لكلهم\n" +
"لكلهن\n" +
"لكلنا\n" +
"وكلكم\n" +
"وكلكن\n" +
"وكلها\n" +
"وكلهم\n" +
"وكلهن\n" +
"وكلنا\n" +
"فكلكم\n" +
"فكلكن\n" +
"فكلها\n" +
"فكلهم\n" +
"فكلهن\n" +
"فكلنا\n" +
"وبكلي\n" +
"وبكلك\n" +
"وبكله\n" +
"وبكلي\n" +
"فبكلي\n" +
"فبكلك\n" +
"فبكله\n" +
"فبكلي\n" +
"وككلي\n" +
"وككلك\n" +
"وككله\n" +
"وككلي\n" +
"فككلي\n" +
"فككلك\n" +
"فككله\n" +
"فككلي\n" +
"ولكلي\n" +
"ولكلك\n" +
"ولكله\n" +
"ولكلي\n" +
"فلكلي\n" +
"فلكلك\n" +
"فلكله\n" +
"فلكلي\n" +
"ولكلي\n" +
"ولكلك\n" +
"ولكله\n" +
"ولكلي\n" +
"فلكلي\n" +
"فلكلك\n" +
"فلكله\n" +
"فلكلي\n" +
"اكلكم\n" +
"اكلكن\n" +
"اكلها\n" +
"اكلهم\n" +
"اكلهن\n" +
"اكلنا\n" +
"ابكلي\n" +
"ابكلك\n" +
"ابكله\n" +
"ابكلي\n" +
"اككلي\n" +
"اككلك\n" +
"اككله\n" +
"اككلي\n" +
"الكلي\n" +
"الكلك\n" +
"الكله\n" +
"الكلي\n" +
"الكلي\n" +
"الكلك\n" +
"الكله\n" +
"الكلي\n" +
"اوكلي\n" +
"اوكلك\n" +
"اوكله\n" +
"اوكلي\n" +
"افكلي\n" +
"افكلك\n" +
"افكله\n" +
"افكلي\n" +
"ولعمر\n" +
"فلعمر\n" +
"لعمري\n" +
"لعمرك\n" +
"لعمره\n" +
"لعمري\n" +
"اولما\n" +
"افلما\n" +
"وبمثل\n" +
"فبمثل\n" +
"وكمثل\n" +
"فكمثل\n" +
"ولمثل\n" +
"فلمثل\n" +
"ولمثل\n" +
"فلمثل\n" +
"ابمثل\n" +
"اكمثل\n" +
"المثل\n" +
"المثل\n" +
"اومثل\n" +
"افمثل\n" +
"مثلكم\n" +
"مثلكن\n" +
"مثلها\n" +
"مثلهم\n" +
"مثلهن\n" +
"مثلنا\n" +
"بمثلي\n" +
"بمثلك\n" +
"بمثله\n" +
"بمثلي\n" +
"كمثلي\n" +
"كمثلك\n" +
"كمثله\n" +
"كمثلي\n" +
"لمثلي\n" +
"لمثلك\n" +
"لمثله\n" +
"لمثلي\n" +
"لمثلي\n" +
"لمثلك\n" +
"لمثله\n" +
"لمثلي\n" +
"ومثلي\n" +
"ومثلك\n" +
"ومثله\n" +
"ومثلي\n" +
"فمثلي\n" +
"فمثلك\n" +
"فمثله\n" +
"فمثلي\n" +
"امثلي\n" +
"امثلك\n" +
"امثله\n" +
"امثلي\n" +
"اولمع\n" +
"افلمع\n" +
"معكما\n" +
"معهما\n" +
"لمعكم\n" +
"لمعكن\n" +
"لمعها\n" +
"لمعهم\n" +
"لمعهن\n" +
"لمعنا\n" +
"ومعكم\n" +
"ومعكن\n" +
"ومعها\n" +
"ومعهم\n" +
"ومعهن\n" +
"ومعنا\n" +
"فمعكم\n" +
"فمعكن\n" +
"فمعها\n" +
"فمعهم\n" +
"فمعهن\n" +
"فمعنا\n" +
"ولمعي\n" +
"ولمعك\n" +
"ولمعه\n" +
"ولمعي\n" +
"فلمعي\n" +
"فلمعك\n" +
"فلمعه\n" +
"فلمعي\n" +
"امعكم\n" +
"امعكن\n" +
"امعها\n" +
"امعهم\n" +
"امعهن\n" +
"امعنا\n" +
"المعي\n" +
"المعك\n" +
"المعه\n" +
"المعي\n" +
"اومعي\n" +
"اومعك\n" +
"اومعه\n" +
"اومعي\n" +
"افمعي\n" +
"افمعك\n" +
"افمعه\n" +
"افمعي\n" +
"ومعاذ\n" +
"فمعاذ\n" +
"معاذي\n" +
"معاذك\n" +
"معاذه\n" +
"معاذي\n" +
"وبنحو\n" +
"فبنحو\n" +
"وكنحو\n" +
"فكنحو\n" +
"ولنحو\n" +
"فلنحو\n" +
"ابنحو\n" +
"اكنحو\n" +
"النحو\n" +
"اونحو\n" +
"افنحو\n" +
"نحوكم\n" +
"نحوكن\n" +
"نحوها\n" +
"نحوهم\n" +
"نحوهن\n" +
"نحونا\n" +
"بنحوي\n" +
"بنحوك\n" +
"بنحوه\n" +
"بنحوي\n" +
"كنحوي\n" +
"كنحوك\n" +
"كنحوه\n" +
"كنحوي\n" +
"لنحوي\n" +
"لنحوك\n" +
"لنحوه\n" +
"لنحوي\n" +
"ونحوي\n" +
"ونحوك\n" +
"ونحوه\n" +
"ونحوي\n" +
"فنحوي\n" +
"فنحوك\n" +
"فنحوه\n" +
"فنحوي\n" +
"انحوي\n" +
"انحوك\n" +
"انحوه\n" +
"انحوي\n" +
"وباقل\n" +
"فباقل\n" +
"وكاقل\n" +
"فكاقل\n" +
"ولاقل\n" +
"فلاقل\n" +
"اقلكم\n" +
"اقلكن\n" +
"اقلها\n" +
"اقلهم\n" +
"اقلهن\n" +
"اقلنا\n" +
"باقلي\n" +
"باقلك\n" +
"باقله\n" +
"باقلي\n" +
"كاقلي\n" +
"كاقلك\n" +
"كاقله\n" +
"كاقلي\n" +
"لاقلي\n" +
"لاقلك\n" +
"لاقله\n" +
"لاقلي\n" +
"واقلي\n" +
"واقلك\n" +
"واقله\n" +
"واقلي\n" +
"فاقلي\n" +
"فاقلك\n" +
"فاقله\n" +
"فاقلي\n" +
"باكثر\n" +
"كاكثر\n" +
"لاكثر\n" +
"واكثر\n" +
"فاكثر\n" +
"اكثري\n" +
"اكثرك\n" +
"اكثره\n" +
"اكثري\n" +
"وامين\n" +
"فامين\n" +
"امامك\n" +
"امامك\n" +
"واليك\n" +
"فاليك\n" +
"واليك\n" +
"فاليك\n" +
"واليك\n" +
"فاليك\n" +
"اليكم\n" +
"اليكم\n" +
"اليكن\n" +
"وبطان\n" +
"فبطان\n" +
"وحذار\n" +
"فحذار\n" +
"ودونك\n" +
"فدونك\n" +
"رويدك\n" +
"سرعان\n" +
"وشتان\n" +
"فشتان\n" +
"وعليك\n" +
"فعليك\n" +
"مكانك\n" +
"مكانك\n" +
"مكانك\n" +
"وهاؤم\n" +
"فهاؤم\n" +
"هيهات\n" +
"وراءك\n" +
"وشكان\n" +
"ويكان\n" +
"الالي\n" +
"للالي\n" +
"بالتي\n" +
"كالتي\n" +
"والتي\n" +
"فالتي\n" +
"وللتي\n" +
"فللتي\n" +
"بالذي\n" +
"كالذي\n" +
"والذي\n" +
"فالذي\n" +
"وللذي\n" +
"فللذي\n" +
"الذين\n" +
"للذين\n" +
"للائي\n" +
"للاتي\n" +
"للتيا\n" +
"للتين\n" +
"للذين\n" +
"وبذات\n" +
"فبذات\n" +
"وكذات\n" +
"فكذات\n" +
"ولذات\n" +
"فلذات\n" +
"اوبمن\n" +
"افبمن\n" +
"اوكمن\n" +
"افكمن\n" +
"اولمن\n" +
"افلمن\n" +
"ابكما\n" +
"ابهما\n" +
"بابكم\n" +
"بابكن\n" +
"بابها\n" +
"بابهم\n" +
"بابهن\n" +
"بابنا\n" +
"الباب\n" +
"كابكم\n" +
"كابكن\n" +
"كابها\n" +
"كابهم\n" +
"كابهن\n" +
"كابنا\n" +
"الكاب\n" +
"لابكم\n" +
"لابكن\n" +
"لابها\n" +
"لابهم\n" +
"لابهن\n" +
"لابنا\n" +
"اللاب\n" +
"وابكم\n" +
"وابكن\n" +
"وابها\n" +
"وابهم\n" +
"وابهن\n" +
"وابنا\n" +
"الواب\n" +
"فابكم\n" +
"فابكن\n" +
"فابها\n" +
"فابهم\n" +
"فابهن\n" +
"فابنا\n" +
"الفاب\n" +
"وبابي\n" +
"وبابك\n" +
"وبابه\n" +
"وبابي\n" +
"فبابي\n" +
"فبابك\n" +
"فبابه\n" +
"فبابي\n" +
"وكابي\n" +
"وكابك\n" +
"وكابه\n" +
"وكابي\n" +
"فكابي\n" +
"فكابك\n" +
"فكابه\n" +
"فكابي\n" +
"ولابي\n" +
"ولابك\n" +
"ولابه\n" +
"ولابي\n" +
"فلابي\n" +
"فلابك\n" +
"فلابه\n" +
"فلابي\n" +
"اخكما\n" +
"اخهما\n" +
"باخكم\n" +
"باخكن\n" +
"باخها\n" +
"باخهم\n" +
"باخهن\n" +
"باخنا\n" +
"الباخ\n" +
"كاخكم\n" +
"كاخكن\n" +
"كاخها\n" +
"كاخهم\n" +
"كاخهن\n" +
"كاخنا\n" +
"الكاخ\n" +
"لاخكم\n" +
"لاخكن\n" +
"لاخها\n" +
"لاخهم\n" +
"لاخهن\n" +
"لاخنا\n" +
"اللاخ\n" +
"واخكم\n" +
"واخكن\n" +
"واخها\n" +
"واخهم\n" +
"واخهن\n" +
"واخنا\n" +
"الواخ\n" +
"فاخكم\n" +
"فاخكن\n" +
"فاخها\n" +
"فاخهم\n" +
"فاخهن\n" +
"فاخنا\n" +
"الفاخ\n" +
"وباخي\n" +
"وباخك\n" +
"وباخه\n" +
"وباخي\n" +
"فباخي\n" +
"فباخك\n" +
"فباخه\n" +
"فباخي\n" +
"وكاخي\n" +
"وكاخك\n" +
"وكاخه\n" +
"وكاخي\n" +
"فكاخي\n" +
"فكاخك\n" +
"فكاخه\n" +
"فكاخي\n" +
"ولاخي\n" +
"ولاخك\n" +
"ولاخه\n" +
"ولاخي\n" +
"فلاخي\n" +
"فلاخك\n" +
"فلاخه\n" +
"فلاخي\n" +
"حمكما\n" +
"حمهما\n" +
"بحمكم\n" +
"بحمكن\n" +
"بحمها\n" +
"بحمهم\n" +
"بحمهن\n" +
"بحمنا\n" +
"البحم\n" +
"كحمكم\n" +
"كحمكن\n" +
"كحمها\n" +
"كحمهم\n" +
"كحمهن\n" +
"كحمنا\n" +
"الكحم\n" +
"لحمكم\n" +
"لحمكن\n" +
"لحمها\n" +
"لحمهم\n" +
"لحمهن\n" +
"لحمنا\n" +
"اللحم\n" +
"وحمكم\n" +
"وحمكن\n" +
"وحمها\n" +
"وحمهم\n" +
"وحمهن\n" +
"وحمنا\n" +
"الوحم\n" +
"فحمكم\n" +
"فحمكن\n" +
"فحمها\n" +
"فحمهم\n" +
"فحمهن\n" +
"فحمنا\n" +
"الفحم\n" +
"وبحمي\n" +
"وبحمك\n" +
"وبحمه\n" +
"وبحمي\n" +
"فبحمي\n" +
"فبحمك\n" +
"فبحمه\n" +
"فبحمي\n" +
"وكحمي\n" +
"وكحمك\n" +
"وكحمه\n" +
"وكحمي\n" +
"فكحمي\n" +
"فكحمك\n" +
"فكحمه\n" +
"فكحمي\n" +
"ولحمي\n" +
"ولحمك\n" +
"ولحمه\n" +
"ولحمي\n" +
"فلحمي\n" +
"فلحمك\n" +
"فلحمه\n" +
"فلحمي\n" +
"فوكما\n" +
"فوهما\n" +
"بفوكم\n" +
"بفوكن\n" +
"بفوها\n" +
"بفوهم\n" +
"بفوهن\n" +
"بفونا\n" +
"البفو\n" +
"كفوكم\n" +
"كفوكن\n" +
"كفوها\n" +
"كفوهم\n" +
"كفوهن\n" +
"كفونا\n" +
"الكفو\n" +
"لفوكم\n" +
"لفوكن\n" +
"لفوها\n" +
"لفوهم\n" +
"لفوهن\n" +
"لفونا\n" +
"اللفو\n" +
"وفوكم\n" +
"وفوكن\n" +
"وفوها\n" +
"وفوهم\n" +
"وفوهن\n" +
"وفونا\n" +
"الوفو\n" +
"ففوكم\n" +
"ففوكن\n" +
"ففوها\n" +
"ففوهم\n" +
"ففوهن\n" +
"ففونا\n" +
"الففو\n" +
"وبفوي\n" +
"وبفوك\n" +
"وبفوه\n" +
"وبفوي\n" +
"فبفوي\n" +
"فبفوك\n" +
"فبفوه\n" +
"فبفوي\n" +
"وكفوي\n" +
"وكفوك\n" +
"وكفوه\n" +
"وكفوي\n" +
"فكفوي\n" +
"فكفوك\n" +
"فكفوه\n" +
"فكفوي\n" +
"ولفوي\n" +
"ولفوك\n" +
"ولفوه\n" +
"ولفوي\n" +
"فلفوي\n" +
"فلفوك\n" +
"فلفوه\n" +
"فلفوي\n" +
"ولولا\n" +
"فلولا\n" +
"ولوما\n" +
"فلوما\n" +
"وحبذا\n" +
"فحبذا\n" +
"ساءما\n" +
"ونعما\n" +
"فنعما\n" +
"انكما\n" +
"انهما\n" +
"بانكم\n" +
"بانكن\n" +
"بانها\n" +
"بانهم\n" +
"بانهن\n" +
"باننا\n" +
"كانكم\n" +
"كانكن\n" +
"كانها\n" +
"كانهم\n" +
"كانهن\n" +
"كاننا\n" +
"لانكم\n" +
"لانكن\n" +
"لانها\n" +
"لانهم\n" +
"لانهن\n" +
"لاننا\n" +
"وانكم\n" +
"وانكن\n" +
"وانها\n" +
"وانهم\n" +
"وانهن\n" +
"واننا\n" +
"فانكم\n" +
"فانكن\n" +
"فانها\n" +
"فانهم\n" +
"فانهن\n" +
"فاننا\n" +
"وباني\n" +
"وبانك\n" +
"وبانه\n" +
"وباني\n" +
"فباني\n" +
"فبانك\n" +
"فبانه\n" +
"فباني\n" +
"وكاني\n" +
"وكانك\n" +
"وكانه\n" +
"وكاني\n" +
"فكاني\n" +
"فكانك\n" +
"فكانه\n" +
"فكاني\n" +
"ولاني\n" +
"ولانك\n" +
"ولانه\n" +
"ولاني\n" +
"فلاني\n" +
"فلانك\n" +
"فلانه\n" +
"فلاني\n" +
"اوبان\n" +
"افبان\n" +
"اوكان\n" +
"افكان\n" +
"اولان\n" +
"افلان\n" +
"انكما\n" +
"انهما\n" +
"بانكم\n" +
"بانكن\n" +
"بانها\n" +
"بانهم\n" +
"بانهن\n" +
"باننا\n" +
"كانكم\n" +
"كانكن\n" +
"كانها\n" +
"كانهم\n" +
"كانهن\n" +
"كاننا\n" +
"لانكم\n" +
"لانكن\n" +
"لانها\n" +
"لانهم\n" +
"لانهن\n" +
"لاننا\n" +
"وانكم\n" +
"وانكن\n" +
"وانها\n" +
"وانهم\n" +
"وانهن\n" +
"واننا\n" +
"فانكم\n" +
"فانكن\n" +
"فانها\n" +
"فانهم\n" +
"فانهن\n" +
"فاننا\n" +
"وباني\n" +
"وبانك\n" +
"وبانه\n" +
"وباني\n" +
"فباني\n" +
"فبانك\n" +
"فبانه\n" +
"فباني\n" +
"وكاني\n" +
"وكانك\n" +
"وكانه\n" +
"وكاني\n" +
"فكاني\n" +
"فكانك\n" +
"فكانه\n" +
"فكاني\n" +
"ولاني\n" +
"ولانك\n" +
"ولانه\n" +
"ولاني\n" +
"فلاني\n" +
"فلانك\n" +
"فلانه\n" +
"فلاني\n" +
"اانكم\n" +
"اانكن\n" +
"اانها\n" +
"اانهم\n" +
"اانهن\n" +
"ااننا\n" +
"اباني\n" +
"ابانك\n" +
"ابانه\n" +
"اباني\n" +
"اكاني\n" +
"اكانك\n" +
"اكانه\n" +
"اكاني\n" +
"الاني\n" +
"الانك\n" +
"الانه\n" +
"الاني\n" +
"اواني\n" +
"اوانك\n" +
"اوانه\n" +
"اواني\n" +
"افاني\n" +
"افانك\n" +
"افانه\n" +
"افاني\n" +
"ولكان\n" +
"فلكان\n" +
"الكان\n" +
"اوكان\n" +
"افكان\n" +
"كانكم\n" +
"كانكن\n" +
"كانها\n" +
"كانهم\n" +
"كانهن\n" +
"كاننا\n" +
"لكاني\n" +
"لكانك\n" +
"لكانه\n" +
"لكاني\n" +
"وكاني\n" +
"وكانك\n" +
"وكانه\n" +
"وكاني\n" +
"فكاني\n" +
"فكانك\n" +
"فكانه\n" +
"فكاني\n" +
"اكاني\n" +
"اكانك\n" +
"اكانه\n" +
"اكاني\n" +
"اولعل\n" +
"افلعل\n" +
"لعلكم\n" +
"لعلكن\n" +
"لعلها\n" +
"لعلهم\n" +
"لعلهن\n" +
"لعلنا\n" +
"ولعلي\n" +
"ولعلك\n" +
"ولعله\n" +
"ولعلي\n" +
"فلعلي\n" +
"فلعلك\n" +
"فلعله\n" +
"فلعلي\n" +
"العلي\n" +
"العلك\n" +
"العله\n" +
"العلي\n" +
"لكنكم\n" +
"لكنكن\n" +
"لكنها\n" +
"لكنهم\n" +
"لكنهن\n" +
"لكننا\n" +
"ولكني\n" +
"ولكنك\n" +
"ولكنه\n" +
"ولكني\n" +
"فلكني\n" +
"فلكنك\n" +
"فلكنه\n" +
"فلكني\n" +
"ليتكم\n" +
"ليتكن\n" +
"ليتها\n" +
"ليتهم\n" +
"ليتهن\n" +
"ليتنا\n" +
"وليتي\n" +
"وليتك\n" +
"وليته\n" +
"وليتي\n" +
"فليتي\n" +
"فليتك\n" +
"فليته\n" +
"فليتي\n" +
"باجمع\n" +
"كاجمع\n" +
"لاجمع\n" +
"لاجمع\n" +
"واجمع\n" +
"فاجمع\n" +
"ااجمع\n" +
"اجمعي\n" +
"اجمعك\n" +
"اجمعه\n" +
"اجمعي\n" +
"بجميع\n" +
"كجميع\n" +
"لجميع\n" +
"لجميع\n" +
"وجميع\n" +
"فجميع\n" +
"اجميع\n" +
"جميعي\n" +
"جميعك\n" +
"جميعه\n" +
"جميعي\n" +
"بعامة\n" +
"كعامة\n" +
"لعامة\n" +
"لعامة\n" +
"وعامة\n" +
"فعامة\n" +
"اعامة\n" +
"عامتي\n" +
"عامتك\n" +
"عامته\n" +
"عامتي\n" +
"وبعين\n" +
"فبعين\n" +
"وكعين\n" +
"فكعين\n" +
"ولعين\n" +
"فلعين\n" +
"ولعين\n" +
"فلعين\n" +
"ابعين\n" +
"اكعين\n" +
"العين\n" +
"العين\n" +
"اوعين\n" +
"افعين\n" +
"عينكم\n" +
"عينكن\n" +
"عينها\n" +
"عينهم\n" +
"عينهن\n" +
"عيننا\n" +
"بعيني\n" +
"بعينك\n" +
"بعينه\n" +
"بعيني\n" +
"كعيني\n" +
"كعينك\n" +
"كعينه\n" +
"كعيني\n" +
"لعيني\n" +
"لعينك\n" +
"لعينه\n" +
"لعيني\n" +
"لعيني\n" +
"لعينك\n" +
"لعينه\n" +
"لعيني\n" +
"وعيني\n" +
"وعينك\n" +
"وعينه\n" +
"وعيني\n" +
"فعيني\n" +
"فعينك\n" +
"فعينه\n" +
"فعيني\n" +
"اعيني\n" +
"اعينك\n" +
"اعينه\n" +
"اعيني\n" +
"اوبكل\n" +
"افبكل\n" +
"اوككل\n" +
"افككل\n" +
"اولكل\n" +
"افلكل\n" +
"اولكل\n" +
"افلكل\n" +
"كلكما\n" +
"كلهما\n" +
"بكلكم\n" +
"بكلكن\n" +
"بكلها\n" +
"بكلهم\n" +
"بكلهن\n" +
"بكلنا\n" +
"ككلكم\n" +
"ككلكن\n" +
"ككلها\n" +
"ككلهم\n" +
"ككلهن\n" +
"ككلنا\n" +
"لكلكم\n" +
"لكلكن\n" +
"لكلها\n" +
"لكلهم\n" +
"لكلهن\n" +
"لكلنا\n" +
"لكلكم\n" +
"لكلكن\n" +
"لكلها\n" +
"لكلهم\n" +
"لكلهن\n" +
"لكلنا\n" +
"وكلكم\n" +
"وكلكن\n" +
"وكلها\n" +
"وكلهم\n" +
"وكلهن\n" +
"وكلنا\n" +
"فكلكم\n" +
"فكلكن\n" +
"فكلها\n" +
"فكلهم\n" +
"فكلهن\n" +
"فكلنا\n" +
"وبكلي\n" +
"وبكلك\n" +
"وبكله\n" +
"وبكلي\n" +
"فبكلي\n" +
"فبكلك\n" +
"فبكله\n" +
"فبكلي\n" +
"وككلي\n" +
"وككلك\n" +
"وككله\n" +
"وككلي\n" +
"فككلي\n" +
"فككلك\n" +
"فككله\n" +
"فككلي\n" +
"ولكلي\n" +
"ولكلك\n" +
"ولكله\n" +
"ولكلي\n" +
"فلكلي\n" +
"فلكلك\n" +
"فلكله\n" +
"فلكلي\n" +
"ولكلي\n" +
"ولكلك\n" +
"ولكله\n" +
"ولكلي\n" +
"فلكلي\n" +
"فلكلك\n" +
"فلكله\n" +
"فلكلي\n" +
"اكلكم\n" +
"اكلكن\n" +
"اكلها\n" +
"اكلهم\n" +
"اكلهن\n" +
"اكلنا\n" +
"ابكلي\n" +
"ابكلك\n" +
"ابكله\n" +
"ابكلي\n" +
"اككلي\n" +
"اككلك\n" +
"اككله\n" +
"اككلي\n" +
"الكلي\n" +
"الكلك\n" +
"الكله\n" +
"الكلي\n" +
"الكلي\n" +
"الكلك\n" +
"الكله\n" +
"الكلي\n" +
"اوكلي\n" +
"اوكلك\n" +
"اوكله\n" +
"اوكلي\n" +
"افكلي\n" +
"افكلك\n" +
"افكله\n" +
"افكلي\n" +
"اوكلا\n" +
"افكلا\n" +
"بكلتا\n" +
"ككلتا\n" +
"لكلتا\n" +
"لكلتا\n" +
"وكلتا\n" +
"فكلتا\n" +
"اكلتا\n" +
"وبنفس\n" +
"فبنفس\n" +
"وكنفس\n" +
"فكنفس\n" +
"ولنفس\n" +
"فلنفس\n" +
"ولنفس\n" +
"فلنفس\n" +
"ابنفس\n" +
"اكنفس\n" +
"النفس\n" +
"النفس\n" +
"اونفس\n" +
"افنفس\n" +
"نفسكم\n" +
"نفسكن\n" +
"نفسها\n" +
"نفسهم\n" +
"نفسهن\n" +
"نفسنا\n" +
"بنفسي\n" +
"بنفسك\n" +
"بنفسه\n" +
"بنفسي\n" +
"كنفسي\n" +
"كنفسك\n" +
"كنفسه\n" +
"كنفسي\n" +
"لنفسي\n" +
"لنفسك\n" +
"لنفسه\n" +
"لنفسي\n" +
"لنفسي\n" +
"لنفسك\n" +
"لنفسه\n" +
"لنفسي\n" +
"ونفسي\n" +
"ونفسك\n" +
"ونفسه\n" +
"ونفسي\n" +
"فنفسي\n" +
"فنفسك\n" +
"فنفسه\n" +
"فنفسي\n" +
"انفسي\n" +
"انفسك\n" +
"انفسه\n" +
"انفسي\n" +
"وحاشا\n" +
"فحاشا\n" +
"حاشاي\n" +
"حاشاك\n" +
"حاشاه\n" +
"حاشاي\n" +
"خلاكم\n" +
"خلاكن\n" +
"خلاها\n" +
"خلاهم\n" +
"خلاهن\n" +
"خلانا\n" +
"وخلاي\n" +
"وخلاك\n" +
"وخلاه\n" +
"وخلاي\n" +
"فخلاي\n" +
"فخلاك\n" +
"فخلاه\n" +
"فخلاي\n" +
"عداكم\n" +
"عداكن\n" +
"عداها\n" +
"عداهم\n" +
"عداهن\n" +
"عدانا\n" +
"وعداي\n" +
"وعداك\n" +
"وعداه\n" +
"وعداي\n" +
"فعداي\n" +
"فعداك\n" +
"فعداه\n" +
"فعداي\n" +
"لكنكم\n" +
"لكنكن\n" +
"لكنها\n" +
"لكنهم\n" +
"لكنهن\n" +
"لكننا\n" +
"ولكني\n" +
"ولكنك\n" +
"ولكنه\n" +
"ولكني\n" +
"فلكني\n" +
"فلكنك\n" +
"فلكنه\n" +
"فلكني\n" +
"وفيما\n" +
"ففيما\n" +
"ولسوف\n" +
"فلسوف\n" +
"كانما\n" +
"لكيلا\n" +
"ولالي\n" +
"فلالي\n" +
"الالي\n" +
"اوالي\n" +
"افالي\n" +
"اليكم\n" +
"اليكن\n" +
"اليها\n" +
"اليهم\n" +
"اليهن\n" +
"الينا\n" +
"لاليك\n" +
"لاليه\n" +
"واليك\n" +
"واليه\n" +
"فاليك\n" +
"فاليه\n" +
"ولالي\n" +
"ولالي\n" +
"فلالي\n" +
"فلالي\n" +
"االيك\n" +
"االيه\n" +
"الالي\n" +
"الالي\n" +
"اوالي\n" +
"اوالي\n" +
"افالي\n" +
"افالي\n" +
"ولعلي\n" +
"فلعلي\n" +
"العلي\n" +
"اوعلي\n" +
"افعلي\n" +
"عليكم\n" +
"عليكن\n" +
"عليها\n" +
"عليهم\n" +
"عليهن\n" +
"علينا\n" +
"لعليك\n" +
"لعليه\n" +
"وعليك\n" +
"وعليه\n" +
"فعليك\n" +
"فعليه\n" +
"ولعلي\n" +
"ولعلي\n" +
"فلعلي\n" +
"فلعلي\n" +
"اعليك\n" +
"اعليه\n" +
"العلي\n" +
"العلي\n" +
"اوعلي\n" +
"اوعلي\n" +
"افعلي\n" +
"افعلي\n" +
"اولعن\n" +
"افلعن\n" +
"عنكما\n" +
"عنهما\n" +
"لعنكم\n" +
"لعنكن\n" +
"لعنها\n" +
"لعنهم\n" +
"لعنهن\n" +
"لعننا\n" +
"وعنكم\n" +
"وعنكن\n" +
"وعنها\n" +
"وعنهم\n" +
"وعنهن\n" +
"وعننا\n" +
"فعنكم\n" +
"فعنكن\n" +
"فعنها\n" +
"فعنهم\n" +
"فعنهن\n" +
"فعننا\n" +
"ولعني\n" +
"ولعنك\n" +
"ولعنه\n" +
"ولعني\n" +
"فلعني\n" +
"فلعنك\n" +
"فلعنه\n" +
"فلعني\n" +
"اعنكم\n" +
"اعنكن\n" +
"اعنها\n" +
"اعنهم\n" +
"اعنهن\n" +
"اعننا\n" +
"العني\n" +
"العنك\n" +
"العنه\n" +
"العني\n" +
"اوعني\n" +
"اوعنك\n" +
"اوعنه\n" +
"اوعني\n" +
"افعني\n" +
"افعنك\n" +
"افعنه\n" +
"افعني\n" +
"اولفي\n" +
"افلفي\n" +
"فيكما\n" +
"فيهما\n" +
"لفيكم\n" +
"لفيكن\n" +
"لفيها\n" +
"لفيهم\n" +
"لفيهن\n" +
"لفينا\n" +
"وفيكم\n" +
"وفيكن\n" +
"وفيها\n" +
"وفيهم\n" +
"وفيهن\n" +
"وفينا\n" +
"ففيكم\n" +
"ففيكن\n" +
"ففيها\n" +
"ففيهم\n" +
"ففيهن\n" +
"ففينا\n" +
"ولفيي\n" +
"ولفيك\n" +
"ولفيه\n" +
"ولفيي\n" +
"فلفيي\n" +
"فلفيك\n" +
"فلفيه\n" +
"فلفيي\n" +
"افيكم\n" +
"افيكن\n" +
"افيها\n" +
"افيهم\n" +
"افيهن\n" +
"افينا\n" +
"الفيي\n" +
"الفيك\n" +
"الفيه\n" +
"الفيي\n" +
"اوفيي\n" +
"اوفيك\n" +
"اوفيه\n" +
"اوفيي\n" +
"اففيي\n" +
"اففيك\n" +
"اففيه\n" +
"اففيي\n" +
"اولمن\n" +
"افلمن\n" +
"منكما\n" +
"منهما\n" +
"لمنكم\n" +
"لمنكن\n" +
"لمنها\n" +
"لمنهم\n" +
"لمنهن\n" +
"لمننا\n" +
"ومنكم\n" +
"ومنكن\n" +
"ومنها\n" +
"ومنهم\n" +
"ومنهن\n" +
"ومننا\n" +
"فمنكم\n" +
"فمنكن\n" +
"فمنها\n" +
"فمنهم\n" +
"فمنهن\n" +
"فمننا\n" +
"ولمني\n" +
"ولمنك\n" +
"ولمنه\n" +
"ولمني\n" +
"فلمني\n" +
"فلمنك\n" +
"فلمنه\n" +
"فلمني\n" +
"امنكم\n" +
"امنكن\n" +
"امنها\n" +
"امنهم\n" +
"امنهن\n" +
"امننا\n" +
"المني\n" +
"المنك\n" +
"المنه\n" +
"المني\n" +
"اومني\n" +
"اومنك\n" +
"اومنه\n" +
"اومني\n" +
"افمني\n" +
"افمنك\n" +
"افمنه\n" +
"افمني\n" +
"اولما\n" +
"افلما\n" +
"واذما\n" +
"فاذما\n" +
"اوبكم\n" +
"افبكم\n" +
"وبكما\n" +
"فبكما\n" +
"ابكما\n" +
"اوبكن\n" +
"افبكن\n" +
"اوبنا\n" +
"افبنا\n" +
"اوبها\n" +
"افبها\n" +
"اولكم\n" +
"افلكم\n" +
"ولكما\n" +
"فلكما\n" +
"الكما\n" +
"اولكن\n" +
"افلكن\n" +
"اولنا\n" +
"افلنا\n" +
"اولها\n" +
"افلها\n" +
"ولانا\n" +
"فلانا\n" +
"الانا\n" +
"اوانا\n" +
"افانا\n" +
"ولانت\n" +
"فلانت\n" +
"الانت\n" +
"اوانت\n" +
"افانت\n" +
"ولانت\n" +
"فلانت\n" +
"الانت\n" +
"اوانت\n" +
"افانت\n" +
"لانتم\n" +
"وانتم\n" +
"فانتم\n" +
"اانتم\n" +
"انتما\n" +
"لانتن\n" +
"وانتن\n" +
"فانتن\n" +
"اانتن\n" +
"ولنحن\n" +
"فلنحن\n" +
"النحن\n" +
"اونحن\n" +
"افنحن\n" +
"اوبهم\n" +
"افبهم\n" +
"اوكهم\n" +
"افكهم\n" +
"اولهم\n" +
"افلهم\n" +
"اولهم\n" +
"افلهم\n" +
"وبهما\n" +
"فبهما\n" +
"وكهما\n" +
"فكهما\n" +
"ولهما\n" +
"فلهما\n" +
"ولهما\n" +
"فلهما\n" +
"ابهما\n" +
"اكهما\n" +
"الهما\n" +
"الهما\n" +
"اوهما\n" +
"افهما\n" +
"اوبهن\n" +
"افبهن\n" +
"اوكهن\n" +
"افكهن\n" +
"اولهن\n" +
"افلهن\n" +
"اولهن\n" +
"افلهن\n" +
"اوبهو\n" +
"افبهو\n" +
"اوكهو\n" +
"افكهو\n" +
"اولهو\n" +
"افلهو\n" +
"اولهو\n" +
"افلهو\n" +
"اوبهي\n" +
"افبهي\n" +
"اوكهي\n" +
"افكهي\n" +
"اولهي\n" +
"افلهي\n" +
"اولهي\n" +
"افلهي\n" +
"باياك\n" +
"كاياك\n" +
"لاياك\n" +
"واياك\n" +
"فاياك\n" +
"باياك\n" +
"كاياك\n" +
"لاياك\n" +
"واياك\n" +
"فاياك\n" +
"اياكم\n" +
"اياكن\n" +
"ايانا\n" +
"باياه\n" +
"كاياه\n" +
"لاياه\n" +
"واياه\n" +
"فاياه\n" +
"اياها\n" +
"اياهم\n" +
"اياهن\n" +
"باياي\n" +
"كاياي\n" +
"لاياي\n" +
"واياي\n" +
"فاياي\n" +
"عندكم\n" +
"عندكن\n" +
"عندها\n" +
"عندهم\n" +
"عندهن\n" +
"عندنا\n" +
"وعندي\n" +
"وعندك\n" +
"وعنده\n" +
"وعندي\n" +
"فعندي\n" +
"فعندك\n" +
"فعنده\n" +
"فعندي\n" +
"وكلما\n" +
"فكلما\n" +
"اكلما\n" +
"لدنكم\n" +
"لدنكن\n" +
"لدنها\n" +
"لدنهم\n" +
"لدنهن\n" +
"لدننا\n" +
"ولدني\n" +
"ولدنك\n" +
"ولدنه\n" +
"ولدني\n" +
"فلدني\n" +
"فلدنك\n" +
"فلدنه\n" +
"فلدني\n" +
"اولدي\n" +
"افلدي\n" +
"لديكم\n" +
"لديكن\n" +
"لديها\n" +
"لديهم\n" +
"لديهن\n" +
"لدينا\n" +
"ولديك\n" +
"ولديه\n" +
"فلديك\n" +
"فلديه\n" +
"الديك\n" +
"الديه\n" +
"اولدي\n" +
"اولدي\n" +
"افلدي\n" +
"افلدي\n" +
"اولما\n" +
"افلما\n" +
"والان\n" +
"فالان\n" +
"واناء\n" +
"فاناء\n" +
"وانفا\n" +
"فانفا\n" +
"وابدا\n" +
"فابدا\n" +
"واصلا\n" +
"فاصلا\n" +
"وايان\n" +
"فايان\n" +
"بعدكم\n" +
"بعدكن\n" +
"بعدها\n" +
"بعدهم\n" +
"بعدهن\n" +
"بعدنا\n" +
"وبعدي\n" +
"وبعدك\n" +
"وبعده\n" +
"وبعدي\n" +
"فبعدي\n" +
"فبعدك\n" +
"فبعده\n" +
"فبعدي\n" +
"وتارة\n" +
"فتارة\n" +
"حينكم\n" +
"حينكن\n" +
"حينها\n" +
"حينهم\n" +
"حينهن\n" +
"حيننا\n" +
"وحيني\n" +
"وحينك\n" +
"وحينه\n" +
"وحيني\n" +
"فحيني\n" +
"فحينك\n" +
"فحينه\n" +
"فحيني\n" +
"وصباح\n" +
"فصباح\n" +
"وضحوة\n" +
"فضحوة\n" +
"وغداة\n" +
"فغداة\n" +
"ومساء\n" +
"فمساء\n" +
"يومئذ\n" +
"وخلال\n" +
"فخلال\n" +
"خلالي\n" +
"خلالك\n" +
"خلاله\n" +
"خلالي\n" +
"لامام\n" +
"وامام\n" +
"فامام\n" +
"اامام\n" +
"امامي\n" +
"امامك\n" +
"امامه\n" +
"امامي\n" +
"وازاء\n" +
"فازاء\n" +
"بينكم\n" +
"بينكن\n" +
"بينها\n" +
"بينهم\n" +
"بينهن\n" +
"بيننا\n" +
"وبيني\n" +
"وبينك\n" +
"وبينه\n" +
"وبيني\n" +
"فبيني\n" +
"فبينك\n" +
"فبينه\n" +
"فبيني\n" +
"ولتحت\n" +
"فلتحت\n" +
"التحت\n" +
"اوتحت\n" +
"افتحت\n" +
"تحتكم\n" +
"تحتكن\n" +
"تحتها\n" +
"تحتهم\n" +
"تحتهن\n" +
"تحتنا\n" +
"لتحتي\n" +
"لتحتك\n" +
"لتحته\n" +
"لتحتي\n" +
"وتحتي\n" +
"وتحتك\n" +
"وتحته\n" +
"وتحتي\n" +
"فتحتي\n" +
"فتحتك\n" +
"فتحته\n" +
"فتحتي\n" +
"اتحتي\n" +
"اتحتك\n" +
"اتحته\n" +
"اتحتي\n" +
"ولخلف\n" +
"فلخلف\n" +
"الخلف\n" +
"اوخلف\n" +
"افخلف\n" +
"خلفكم\n" +
"خلفكن\n" +
"خلفها\n" +
"خلفهم\n" +
"خلفهن\n" +
"خلفنا\n" +
"لخلفي\n" +
"لخلفك\n" +
"لخلفه\n" +
"لخلفي\n" +
"وخلفي\n" +
"وخلفك\n" +
"وخلفه\n" +
"وخلفي\n" +
"فخلفي\n" +
"فخلفك\n" +
"فخلفه\n" +
"فخلفي\n" +
"اخلفي\n" +
"اخلفك\n" +
"اخلفه\n" +
"اخلفي\n" +
"لشمال\n" +
"وشمال\n" +
"فشمال\n" +
"اشمال\n" +
"شمالي\n" +
"شمالك\n" +
"شماله\n" +
"شمالي\n" +
"ضمنكم\n" +
"ضمنكن\n" +
"ضمنها\n" +
"ضمنهم\n" +
"ضمنهن\n" +
"ضمننا\n" +
"وضمني\n" +
"وضمنك\n" +
"وضمنه\n" +
"وضمني\n" +
"فضمني\n" +
"فضمنك\n" +
"فضمنه\n" +
"فضمني\n" +
"ولفوق\n" +
"فلفوق\n" +
"الفوق\n" +
"اوفوق\n" +
"اففوق\n" +
"فوقكم\n" +
"فوقكن\n" +
"فوقها\n" +
"فوقهم\n" +
"فوقهن\n" +
"فوقنا\n" +
"لفوقي\n" +
"لفوقك\n" +
"لفوقه\n" +
"لفوقي\n" +
"وفوقي\n" +
"وفوقك\n" +
"وفوقه\n" +
"وفوقي\n" +
"ففوقي\n" +
"ففوقك\n" +
"ففوقه\n" +
"ففوقي\n" +
"افوقي\n" +
"افوقك\n" +
"افوقه\n" +
"افوقي\n" +
"ليمين\n" +
"ويمين\n" +
"فيمين\n" +
"ايمين\n" +
"يميني\n" +
"يمينك\n" +
"يمينه\n" +
"يميني\n" +
"حوالي\n" +
"حوالي\n" +
"حوالي\n" +
"حولكم\n" +
"حولكن\n" +
"حولها\n" +
"حولهم\n" +
"حولهن\n" +
"حولنا\n" +
"وحولي\n" +
"وحولك\n" +
"وحوله\n" +
"وحولي\n" +
"فحولي\n" +
"فحولك\n" +
"فحوله\n" +
"فحولي\n" +
"طالما\n" +
"لقلما\n" +
"وقلما\n" +
"فقلما\n" +
"اقلما\n" +
"ابتدا\n" +
"انبري\n" +
"ولاخذ\n" +
"فلاخذ\n" +
"لاقبل\n" +
"واقبل\n" +
"فاقبل\n" +
"لانشا\n" +
"وانشا\n" +
"فانشا\n" +
"لاوشك\n" +
"واوشك\n" +
"فاوشك\n" +
"ولجعل\n" +
"فلجعل\n" +
"ولحري\n" +
"فلحري\n" +
"ولشرع\n" +
"فلشرع\n" +
"ولطفق\n" +
"فلطفق\n" +
"ولعسي\n" +
"فلعسي\n" +
"ولعلق\n" +
"فلعلق\n" +
"ولقام\n" +
"فلقام\n" +
"ولكاد\n" +
"فلكاد\n" +
"ولكرب\n" +
"فلكرب\n" +
"وانما\n" +
"فانما\n" +
"لكنما\n" +
"لارتد\n" +
"وارتد\n" +
"فارتد\n" +
"انقلب\n" +
"لاصبح\n" +
"واصبح\n" +
"فاصبح\n" +
"لاضحي\n" +
"واضحي\n" +
"فاضحي\n" +
"لامسي\n" +
"وامسي\n" +
"فامسي\n" +
"ولبات\n" +
"فلبات\n" +
"لتبدل\n" +
"وتبدل\n" +
"فتبدل\n" +
"لتحول\n" +
"وتحول\n" +
"فتحول\n" +
"ولحار\n" +
"فلحار\n" +
"ولراح\n" +
"فلراح\n" +
"ولرجع\n" +
"فلرجع\n" +
"ولصار\n" +
"فلصار\n" +
"ولعاد\n" +
"فلعاد\n" +
"ولغدا\n" +
"فلغدا\n" +
"ولكان\n" +
"فلكان\n" +
"مابرح\n" +
"مادام\n" +
"مازال\n" +
"مافتئ\n" +
"اوليس\n" +
"افليس\n" +
"اولست\n" +
"افلست\n" +
"ولسنا\n" +
"فلسنا\n" +
"السنا\n" +
"اولست\n" +
"افلست\n" +
"اولست\n" +
"افلست\n" +
"لستما\n" +
"ولستم\n" +
"فلستم\n" +
"الستم\n" +
"ولستن\n" +
"فلستن\n" +
"الستن\n" +
"وليست\n" +
"فليست\n" +
"اليست\n" +
"وليسا\n" +
"فليسا\n" +
"اليسا\n" +
"ليستا\n" +
"ليسوا\n" +
"اولسن\n" +
"افلسن\n" +
"اوبضع\n" +
"افبضع\n" +
"بضعكم\n" +
"بضعكن\n" +
"بضعها\n" +
"بضعهم\n" +
"بضعهن\n" +
"بضعنا\n" +
"وبضعي\n" +
"وبضعك\n" +
"وبضعه\n" +
"وبضعي\n" +
"فبضعي\n" +
"فبضعك\n" +
"فبضعه\n" +
"فبضعي\n" +
"ابضعي\n" +
"ابضعك\n" +
"ابضعه\n" +
"ابضعي\n" +
"وفلان\n" +
"ففلان\n" +
"افلان\n" +
"وكاين\n" +
"فكاين\n" +
"وكاين\n" +
"فكاين\n" +
"اوكذا\n" +
"افكذا";
       stopWords = stopWords.replace('\n', ' ');
      String[] twoWord= stopWords.split("\\s+");
      for(int i=0;i<twoWord.length;i++)
      {
          if(input.equals(twoWord[i]))
          {
             return "";
          }
      }
      return input;
    }
        public final String remove6stopWords (String input)
    {
        List<String> alf = new ArrayList<String>();
        alf.add("أ");
        alf.add("إ");
        alf.add("آ");
        for(int i =0;i<alf.size();i++)
        {
        input =input.replace(alf.get(i),"ا");
        }
        input =input.replace("ى","ي");
        input = input.trim();
        String replaceMe = Character.toString((char)(65279));
            input= input.replace(replaceMe, "");
            replaceMe = Character.toString((char)(160));
            input = input.replace(replaceMe, " ");  
            input=input.trim();
        return input;
    }
         public final String remove7stopWords (String input)
    {
        List<String> alf = new ArrayList<String>();
        alf.add("أ");
        alf.add("إ");
        alf.add("آ");
        for(int i =0;i<alf.size();i++)
        {
        input =input.replace(alf.get(i),"ا");
        }
        input =input.replace("ى","ي");
        input = input.trim();
        String replaceMe = Character.toString((char)(65279));
            input= input.replace(replaceMe, "");
            replaceMe = Character.toString((char)(160));
            input = input.replace(replaceMe, " ");  
            input=input.trim();
        return input;
    }
          public final String remove8stopWords (String input)
    {
        List<String> alf = new ArrayList<String>();
        alf.add("أ");
        alf.add("إ");
        alf.add("آ");
        for(int i =0;i<alf.size();i++)
        {
        input =input.replace(alf.get(i),"ا");
        }
        input =input.replace("ى","ي");
        input = input.trim();
        String replaceMe = Character.toString((char)(65279));
            input= input.replace(replaceMe, "");
            replaceMe = Character.toString((char)(160));
            input = input.replace(replaceMe, " ");  
            input=input.trim();
        return input;
    }
           public final String remove9stopWords (String input)
    {
        List<String> alf = new ArrayList<String>();
        alf.add("أ");
        alf.add("إ");
        alf.add("آ");
        for(int i =0;i<alf.size();i++)
        {
        input =input.replace(alf.get(i),"ا");
        }
        input =input.replace("ى","ي");
        input = input.trim();
        String replaceMe = Character.toString((char)(65279));
            input= input.replace(replaceMe, "");
            replaceMe = Character.toString((char)(160));
            input = input.replace(replaceMe, " ");  
            input=input.trim();
        return input;
    }
            public final String remove10stopWords (String input)
    {
        List<String> alf = new ArrayList<String>();
        alf.add("أ");
        alf.add("إ");
        alf.add("آ");
        for(int i =0;i<alf.size();i++)
        {
        input =input.replace(alf.get(i),"ا");
        }
        input =input.replace("ى","ي");
        input = input.trim();
        String replaceMe = Character.toString((char)(65279));
            input= input.replace(replaceMe, "");
            replaceMe = Character.toString((char)(160));
            input = input.replace(replaceMe, " ");  
            input=input.trim();
        return input;
    }
}
