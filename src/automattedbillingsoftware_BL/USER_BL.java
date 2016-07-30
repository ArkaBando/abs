package automattedbillingsoftware_BL;

import automatedbillingsoftware_DA.User_DA;
import automatedbillingsoftware_modal.Users;

/**
 *
 * @author Arka
 */
public class USER_BL {

    private User_DA userda;

    public USER_BL() {
        if (userda == null) {
            userda = new User_DA();
        }
    }
    
//    public Users addUser(Users user)
//    {
//        Users
//    }
}
