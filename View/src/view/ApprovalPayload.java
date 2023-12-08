package view;


import java.text.SimpleDateFormat;

import java.util.TimeZone;

public class ApprovalPayload {
    public ApprovalPayload() {
        super();
    }
    public static final String userName="oaopdtst";
    public static final String password="O_0Pst#819";
    
    /***Cloud purpose un comment these section while run in local Machine cloud **/ 
 //public static final String TRANFER_GL_WSDL ="https://oaosoatest.oandoplc.com/soa-infra/services/default/ProcessUoPGLToFusion/processuopgltofusionbpel_client_ep?WSDL\n";
   
    /***Cloud purpose un comment these section while deploying to TEST cloud **/ 
   public static final String TRANFER_GL_WSDL ="http://oaosoatst-wls-1.sub08071802371.oandopaasvcn.oraclevcn.com:9073/soa-infra/services/default/ProcessUoPGLToFusion/processuopgltofusionbpel_client_ep?WSDL";
    // Old TEST instance WSDL
   //public static final String TRANFER_GL_WSDL ="http://oaosoatest-wls-1.sub08071802371.oandopaasvcn.oraclevcn.com:9073/soa-infra/services/default/ProcessUoPGLToFusion/processuopgltofusionbpel_client_ep?WSDL";
   
    /***Cloud purpose un comment these section while deploying to PROD cloud **/    
   // public static final String TRANFER_GL_WSDL ="http://oaosoaprd-wls-1.sub08071802370.oandopaasvcn.oraclevcn.com:9073/soa-infra/services/default/ExtraDutyPayElementEntry/bpelprocess1_client_ep?WSDL";
    
    public static final String TRANFER_GL_METHOD = "process";
    
  
      
    public static String payrollTypeXMLData(String p_EmployeeNumber)
                                              
                                              {
        String[] info=payloadHeader();       
        System.err.println("Created time===>"+info[0]);
        System.err.println("User===========>"+info[1]);
        System.err.println("Password=======>"+info[2]);
        System.err.println("End time=======>"+info[3]);  
        String xmlData="<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" + 
        "     <soap:Body>\n" + 
        "          <ns1:process xmlns:ns1=\"http://xmlns.oracle.com/AspectFusionApplication/ProcessUoPGLToFusion/ProcessUoPGLToFusionBPEL\">\n" + 
        "               <ns1:input>"+p_EmployeeNumber+"</ns1:input>\n" + 
        "        </ns1:process>\n" + 
        "    </soap:Body>\n" + 
        "</soap:Envelope>";
        return xmlData;
    }
      
    public static String[] payloadHeader(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000'Z'"); //Hours:Minutes:Seconds
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.util.Date expDate ;
        expDate = new java.util.Date(t + (10 * 600000000));

    ////        date = new Date();
    //         dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    //         String expDate = dateFormat.format(DateUtils.addMinutes(date, 2));


        java.util.Date plusOne;
    //        String createdTS = dateFormat.format(date);
    //        String expiresTS = dateFormat.format(expDate);
    //        String username="praveen.c@4iapps.com";
    //        String password="Welcome#1234";
         String [] headerInfo=new String[4];
        headerInfo[0]=dateFormat.format(date);
        headerInfo[1]="oaopdtst";
        headerInfo[2]="O_0Pst#819";
        headerInfo[3]=dateFormat.format(expDate);
        return headerInfo;
    }
    
    public static String getUser(){
        
        return null;
    }
    
    
    

    
    
}
