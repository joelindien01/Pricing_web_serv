// Action dans le controller webService
@RestController
@RequestMapping("/api/paiement")
public class TestController {

    @RequestMapping(method = RequestMethod.GET)
    TestModel add() {

        TestModel result = new TestModel();
        result.setPrice(150f);
        
        return result;
    }
}
/// Model decrivant le prix
public class TestModel {

    private float price;

    public TestModel() {
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}


/// Methode a mettre dans le service
private Float convertToHour(String duration) {

        String[] timeDetails = duration.split(":");

        Float hour = new Float(timeDetails[0]);
        Float minute = new Float(timeDetails[1]);
        Float seconds = new Float(timeDetails[2]);

        return hour + minute/60 + seconds/3600;
    }

    private String getDuration(String line) {
        String[] durationData = line.trim().split(",");

        String duration = durationData[0].split("\\s")[1];

        return duration;
    }

    private Float computePrice(String path) {


        String command = "cmd /C D:\\tadigo100515\\ffmpeg-20160531-git-a1953d4-win64-static\\bin\\ffmpeg.exe -i " +path+ " 2>&1| findstr "+ "\"Duration\"" ;

        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {

            process = runtime.exec(command);

        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader input = new BufferedReader(new InputStreamReader((process.getInputStream())));
        String line = null;

        Float hour = null;
        /*Float price = null;*/
        System.out.println(command);
        try {
            while((line = input.readLine()) != null) {

                System.out.println(line);

                 hour = convertToHour( getDuration(line));
                System.out.println(hour);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // pour regler le seuil à partir duquel le client paie
        /*int retVal = Float.compare(hour,0.5f);

        if(retVal <=0){
            price = 0f;
        }
        else{
            price =  hour;
        }*/
        return hour;
    }
