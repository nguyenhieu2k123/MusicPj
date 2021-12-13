package UDP1;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class test {

    public static void main(String[] args) throws Exception{

        Scanner stdIn;
        stdIn = new Scanner(System.in);
        while (true){
                 String str = stdIn.nextLine();
              // Viết hoa chữ cáu đầu 
                char[] charArray = str.toCharArray();
                boolean checkfirstname = true;
                for (int i = 0; i < charArray.length; i++) {
                    if (Character.isLetter(charArray[i])) {
                        if (checkfirstname) {
                            charArray[i] = Character.toUpperCase(charArray[i]);
                            checkfirstname = false;
                        }
                    } else {
                        checkfirstname = true;
                    }
                }
            String result = null;
            str = String.valueOf(charArray);
            System.out.println("Vui lòng nhập:");
            System.out.println(checkinput(str));
            
        }
    }
    // loại bỏ ký tự đặc biệt
    public static String checkinput(String input) {
        Pattern pattern = Pattern.compile("[[^a-z A-Z]&&[^0-9]&&[^ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ']]");
        Matcher matcher = pattern.matcher(input);
        String xoakytuDB = matcher.replaceAll("");
        return xoakytuDB;
    }
}
// kiểm tra đầu vào
// boolean checkinput = true;
//                                        while (true){
//                                                if(checkinput==true){
//                                                      Pattern p = Pattern.compile("^[A-Za-z0-9ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]+$");
//                                                      Matcher m = p.matcher(tmp);
//                                                      checkinput = m.find();
//                                                       if(checkinput == true){
//                                                               System.out.println("hợp lệ");
//                                                       }
//                                                       else{
//                                                               System.out.println("vui lòng không nhập ký tự đặc biệt");
//                                                       }
//                                                }
//                                                break;
//                                        }
//
//                                        byte[] data = tmp.getBytes();
//                                        dpsend = new DatagramPacket(data, data.length, add, desPort);
////                                        System.out.println("Client sent " + tmp + "to" + add.getHostAddress() + "form port" + socket.getLocalPort());
//                                        socket.send(dpsend);
//                                        if(tmp.equals("bye")){
//                                                System.out.println("Client socket closed");
//                                                stdIn.close();
//                                                socket.close();
//                                                break;
//                                        }