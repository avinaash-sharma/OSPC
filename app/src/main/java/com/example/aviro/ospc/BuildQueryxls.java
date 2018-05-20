package com.example.aviro.ospc;

/**
 * Created by USER on 3/22/2018.
 */

public class BuildQueryxls {
    public String getquery(String s1, String s2) {
        String finals;
        String[] arr1 = s1.split("-",3);
        String[] arr2= s2.split("-",3);
        int  m1 = Integer.parseInt(arr1[1]);
        int  y1 = Integer.parseInt(arr1[0]);
        int  m2 = Integer.parseInt(arr2[1]);
        int  y2 = Integer.parseInt(arr2[0]);
        int len = (m2-m1)+((y2-y1)*12)+1;
        String s[]= new String[len];
        //System.out.println(len);
        int j=1,y=y1;
        for(int i=0;i<len;i++)
        {
            if (m1<=12){
                s[i]="(SELECT recorded_time,station_id,parameter_code,recorded_level,min_recorded_level,max_recorded_level FROM db_migrate_pollutant_level_data_"+y1+"_"+m1+" LIMIT 10 OFFSET 10)";
                m1++;
            }
            else if(y2-y1>1){
                if(j<12)
                {
                    y=y1+1;
                    s[i]="(SELECT recorded_time,station_id,parameter_code,recorded_level,min_recorded_level,max_recorded_level FROM db_migrate_pollutant_level_data_"+y+"_"+j+" LIMIT 10 OFFSET 10)";
                    j++;
                }
                else{
                    s[i]="(SELECT recorded_time,station_id,parameter_code,recorded_level,min_recorded_level,max_recorded_level FROM db_migrate_pollutant_level_data_"+y+"_"+j+" LIMIT 10 OFFSET 10)";
                    j=1;
                    y1++;
                }
            }
            else {
                s[i]="(SELECT recorded_time,station_id,parameter_code,recorded_level,min_recorded_level,max_recorded_level FROM db_migrate_pollutant_level_data_"+y2+"_"+j+" LIMIT 10 OFFSET 10)";
                j++;
            }
            //System.out.println(s[i]);
        }
        finals=s[0];
        for (int i=1;i<len;i++)
        {
            finals = finals +" union "+s[i];
        }
        //System.out.println(finals);
	        return finals;
    }



}

