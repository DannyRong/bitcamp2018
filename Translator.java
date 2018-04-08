/******************************************************************************\
* Copyright (C) 2012-2016 Leap Motion, Inc. All rights reserved.               *
* Leap Motion proprietary and confidential. Not for distribution.              *
* Use subject to the terms of the Leap Motion SDK Agreement available at       *
* https://developer.leapmotion.com/sdk_agreement, or another agreement         *
* between Leap Motion and you, your company or other organization.             *
\******************************************************************************/
// Hand Model : Will An
import java.io.IOException;
import java.lang.Math;
import com.leapmotion.leap.*;


class Translator {
    public static void main(String[] args) {
        // Create a sample listener and controller
        SampleListener listener = new SampleListener();
        Controller controller = new Controller();

        // Have the sample listener receive events from the controller
        controller.addListener(listener);

        // Keep this process running until Enter is pressed
        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the sample listener when done
        controller.removeListener(listener);
        
    }
}

class SampleListener extends Listener {
    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
    }

    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    /**
     * input - a hand
     * return - the char it represents
     */
    public String handToLetter(Hand gf)  {
        FingerList fl = gf.fingers();
        FingerList ef = fl.extended();
        String stringEF = "";
        for (int i = 0; i < ef.count(); i++) {
            stringEF = stringEF + " " + ef.get(i).type();
        }
        return stringEF;
    }

    public boolean containsFinger(FingerList list, Finger.Type type)
    {
        int count = list.count();
        for(int i=0; i < count; i++)
        {
            if(list.get(i).type() == type)
                return true;
        }
        return false;
    }

    public String determineNumber(Hand gf) {
        FingerList  extended = gf.fingers().extended();
        String      toReturn = "NO NUMBER DETECTED";

        int count = extended.count();
        switch(count)
        {
            case 1:  //one finger extended; can only be 1, so check
                if(extended.get(0).type() == Finger.Type.TYPE_INDEX)
                    toReturn = drawOne();
                break;
            case 5:  //all five extended; ez pz it's 5
                toReturn = drawFive();
                break;
            case 2:  //two extended; can only be 2 so confirm
                Finger.Type firstType = extended.get(0).type();
                Finger.Type secType = extended.get(1).type();
                if((firstType == Finger.Type.TYPE_INDEX || firstType == Finger.Type.TYPE_MIDDLE) &&
                    (secType == Finger.Type.TYPE_INDEX || secType == Finger.Type.TYPE_MIDDLE))
                    toReturn = drawTwo();
                break;
            case 4:  // 4 fingers extendd can only be 4; check if thumb is there
                if(!containsFinger(extended, Finger.Type.TYPE_THUMB))
                    toReturn = drawFour();
                else
                    toReturn = "NO NUMBER DETECTED";
                break;
            case 3:  //three fingers extended is tricky; 

                //if thumb isn't in it; either 6, 7, 8, or 9
                if(!containsFinger(extended, Finger.Type.TYPE_THUMB))
                {
                    if(!containsFinger(extended, Finger.Type.TYPE_PINKY))
                        toReturn = drawSix();
                    else if(!containsFinger(extended, Finger.Type.TYPE_RING))
                        toReturn = drawSeven();
                    else if(!containsFinger(extended, Finger.Type.TYPE_MIDDLE))
                        toReturn = drawEight();
                    else if(!containsFinger(extended, Finger.Type.TYPE_INDEX))
                        toReturn = drawNine();
                }
                else
                {
                    //test if it's three
                    if(!containsFinger(extended, Finger.Type.TYPE_RING) &&
                       !containsFinger(extended, Finger.Type.TYPE_PINKY))
                        toReturn = drawNine();
                    else
                        toReturn = "NO NUMBER DETECTED";
                }
            
                break;
            default:
                toReturn = "NO NUMBER DETECTED";
        }
        return toReturn;

    }
    
    public String drawOne() {
        String one = "     OOOOOOOOO          NNNNNNNN        NNNNNNNN     EEEEEEEEEEEEEEEEEEEEEE\n   OO:::::::::OO        N:::::::N       N::::::N     E::::::::::::::::::::E\n OO:::::::::::::OO      N::::::::N      N::::::N     E::::::::::::::::::::E\nO:::::::OOO:::::::O     N:::::::::N     N::::::N     EE::::::EEEEEEEEE::::E\nO::::::O   O::::::O     N::::::::::N    N::::::N       E:::::E       EEEEEE\nO:::::O     O:::::O     N:::::::::::N   N::::::N       E:::::E             \nO:::::O     O:::::O     N:::::::N::::N  N::::::N       E::::::EEEEEEEEEE   \nO:::::O     O:::::O     N::::::N N::::N N::::::N       E:::::::::::::::E   \nO:::::O     O:::::O     N::::::N  N::::N:::::::N       E:::::::::::::::E   \nO:::::O     O:::::O     N::::::N   N:::::::::::N       E::::::EEEEEEEEEE   \nO:::::O     O:::::O     N::::::N    N::::::::::N       E:::::E             \nO::::::O   O::::::O     N::::::N     N:::::::::N       E:::::E       EEEEEE\nO:::::::OOO:::::::O     N::::::N      N::::::::N     EE::::::EEEEEEEE:::::E\n OO:::::::::::::OO      N::::::N       N:::::::N     E::::::::::::::::::::E\n   OO:::::::::OO        N::::::N        N::::::N     E::::::::::::::::::::E\n     OOOOOOOOO          NNNNNNNN         NNNNNNN     EEEEEEEEEEEEEEEEEEEEEE\n";
        return one;
    }
    
    public String drawTwo() {
        String two = "TTTTTTTTTTTTTTTTTTTTTTT     WWWWWWWW                           WWWWWWWW          OOOOOOOOO     \nT:::::::::::::::::::::T     W::::::W                           W::::::W        OO:::::::::OO   \nT:::::::::::::::::::::T     W::::::W                           W::::::W      OO:::::::::::::OO \nT:::::TT:::::::TT:::::T     W::::::W                           W::::::W     O:::::::OOO:::::::O\nTTTTTT  T:::::T  TTTTTT      W:::::W           WWWWW           W:::::W      O::::::O   O::::::O\n        T:::::T               W:::::W         W:::::W         W:::::W       O:::::O     O:::::O\n        T:::::T                W:::::W       W:::::::W       W:::::W        O:::::O     O:::::O\n        T:::::T                 W:::::W     W:::::::::W     W:::::W         O:::::O     O:::::O\n        T:::::T                  W:::::W   W:::::W:::::W   W:::::W          O:::::O     O:::::O\n        T:::::T                   W:::::W W:::::W W:::::W W:::::W           O:::::O     O:::::O\n        T:::::T                    W:::::W:::::W   W:::::W:::::W            O:::::O     O:::::O\n        T:::::T                     W:::::::::W     W:::::::::W             O::::::O   O::::::O\n      TT:::::::TT                    W:::::::W       W:::::::W              O:::::::OOO:::::::O\n      T:::::::::T                     W:::::W         W:::::W                OO:::::::::::::OO \n      T:::::::::T                      W:::W           W:::W                   OO:::::::::OO   \n      TTTTTTTTTTT                       WWW             WWW                      OOOOOOOOO     ";
        return two;
    }
    
    public String drawThree() {
        String three = "TTTTTTTTTTTTTTTTTTTTTTT     HHHHHHHHH     HHHHHHHHH     RRRRRRRRRRRRRRRRR        EEEEEEEEEEEEEEEEEEEEEE     EEEEEEEEEEEEEEEEEEEEEE     \nT:::::::::::::::::::::T     H:::::::H     H:::::::H     R::::::::::::::::R       E::::::::::::::::::::E     E::::::::::::::::::::E     \nT:::::::::::::::::::::T     H:::::::H     H:::::::H     R::::::RRRRRR:::::R      E::::::::::::::::::::E     E::::::::::::::::::::E     \nT:::::TT:::::::TT:::::T     HH::::::H     H::::::HH     RR:::::R     R:::::R     EE::::::EEEEEEEEE::::E     EE::::::EEEEEEEEE::::E     \nTTTTTT  T:::::T  TTTTTT       H:::::H     H:::::H         R::::R     R:::::R       E:::::E       EEEEEE       E:::::E       EEEEEE     \n        T:::::T               H:::::H     H:::::H         R::::R     R:::::R       E:::::E                    E:::::E                  \n        T:::::T               H::::::HHHHH::::::H         R::::RRRRRR:::::R        E::::::EEEEEEEEEE          E::::::EEEEEEEEEE        \n        T:::::T               H:::::::::::::::::H         R:::::::::::::RR         E:::::::::::::::E          E:::::::::::::::E        \n        T:::::T               H:::::::::::::::::H         R::::RRRRRR:::::R        E:::::::::::::::E          E:::::::::::::::E        \n        T:::::T               H::::::HHHHH::::::H         R::::R     R:::::R       E::::::EEEEEEEEEE          E::::::EEEEEEEEEE        \n        T:::::T               H:::::H     H:::::H         R::::R     R:::::R       E:::::E                    E:::::E                  \n        T:::::T               H:::::H     H:::::H         R::::R     R:::::R       E:::::E       EEEEEE       E:::::E       EEEEEE     \n      TT:::::::TT           HH::::::H     H::::::HH     RR:::::R     R:::::R     EE::::::EEEEEEEE:::::E     EE::::::EEEEEEEE:::::E     \n      T:::::::::T           H:::::::H     H:::::::H     R::::::R     R:::::R     E::::::::::::::::::::E     E::::::::::::::::::::E     \n      T:::::::::T           H:::::::H     H:::::::H     R::::::R     R:::::R     E::::::::::::::::::::E     E::::::::::::::::::::E     \n      TTTTTTTTTTT           HHHHHHHHH     HHHHHHHHH     RRRRRRRR     RRRRRRR     EEEEEEEEEEEEEEEEEEEEEE     EEEEEEEEEEEEEEEEEEEEEE     \n";
        return three;
    }

    public String drawFour() {
        String four = "FFFFFFFFFFFFFFFFFFFFFF          OOOOOOOOO          UUUUUUUU     UUUUUUUU     RRRRRRRRRRRRRRRRR        \nF::::::::::::::::::::F        OO:::::::::OO        U::::::U     U::::::U     R::::::::::::::::R       \nF::::::::::::::::::::F      OO:::::::::::::OO      U::::::U     U::::::U     R::::::RRRRRR:::::R      \nFF::::::FFFFFFFFF::::F     O:::::::OOO:::::::O     UU:::::U     U:::::UU     RR:::::R     R:::::R     \n  F:::::F       FFFFFF     O::::::O   O::::::O      U:::::U     U:::::U        R::::R     R:::::R     \n  F:::::F                  O:::::O     O:::::O      U:::::D     D:::::U        R::::R     R:::::R     \n  F::::::FFFFFFFFFF        O:::::O     O:::::O      U:::::D     D:::::U        R::::RRRRRR:::::R      \n  F:::::::::::::::F        O:::::O     O:::::O      U:::::D     D:::::U        R:::::::::::::RR       \n  F:::::::::::::::F        O:::::O     O:::::O      U:::::D     D:::::U        R::::RRRRRR:::::R      \n  F::::::FFFFFFFFFF        O:::::O     O:::::O      U:::::D     D:::::U        R::::R     R:::::R     \n  F:::::F                  O:::::O     O:::::O      U:::::D     D:::::U        R::::R     R:::::R     \n  F:::::F                  O::::::O   O::::::O      U::::::U   U::::::U        R::::R     R:::::R     \nFF:::::::FF                O:::::::OOO:::::::O      U:::::::UUU:::::::U      RR:::::R     R:::::R     \nF::::::::FF                 OO:::::::::::::OO        UU:::::::::::::UU       R::::::R     R:::::R     \nF::::::::FF                   OO:::::::::OO            UU:::::::::UU         R::::::R     R:::::R     \nFFFFFFFFFFF                     OOOOOOOOO                UUUUUUUUU           RRRRRRRR     RRRRRRR     ";
        return four;
    }

    public String drawFive() {
        String five = "FFFFFFFFFFFFFFFFFFFFFF     IIIIIIIIII     VVVVVVVV           VVVVVVVV     EEEEEEEEEEEEEEEEEEEEEE\nF::::::::::::::::::::F     I::::::::I     V::::::V           V::::::V     E::::::::::::::::::::E\nF::::::::::::::::::::F     I::::::::I     V::::::V           V::::::V     E::::::::::::::::::::E\nFF::::::FFFFFFFFF::::F     II::::::II     V::::::V           V::::::V     EE::::::EEEEEEEEE::::E\n  F:::::F       FFFFFF       I::::I        V:::::V           V:::::V        E:::::E       EEEEEE\n  F:::::F                    I::::I         V:::::V         V:::::V         E:::::E             \n  F::::::FFFFFFFFFF          I::::I          V:::::V       V:::::V          E::::::EEEEEEEEEE   \n  F:::::::::::::::F          I::::I           V:::::V     V:::::V           E:::::::::::::::E   \n  F:::::::::::::::F          I::::I            V:::::V   V:::::V            E:::::::::::::::E   \n  F::::::FFFFFFFFFF          I::::I             V:::::V V:::::V             E::::::EEEEEEEEEE   \n  F:::::F                    I::::I              V:::::V:::::V              E:::::E             \n  F:::::F                    I::::I               V:::::::::V               E:::::E       EEEEEE\nFF:::::::FF                II::::::II              V:::::::V              EE::::::EEEEEEEE:::::E\nF::::::::FF                I::::::::I               V:::::V               E::::::::::::::::::::E\nF::::::::FF                I::::::::I                V:::V                E::::::::::::::::::::E\nFFFFFFFFFFF                IIIIIIIIII                 VVV                 EEEEEEEEEEEEEEEEEEEEEE\n";
        return five;
    }

    public String drawSix() {
        String six = "   SSSSSSSSSSSSSSS      IIIIIIIIII     XXXXXXX       XXXXXXX\n SS:::::::::::::::S     I::::::::I     X:::::X       X:::::X\nS:::::SSSSSS::::::S     I::::::::I     X:::::X       X:::::X\nS:::::S     SSSSSSS     II::::::II     X::::::X     X::::::X\nS:::::S                   I::::I       XXX:::::X   X:::::XXX\nS:::::S                   I::::I          X:::::X X:::::X   \n S::::SSSS                I::::I           X:::::X:::::X    \n  SS::::::SSSSS           I::::I            X:::::::::X     \n    SSS::::::::SS         I::::I            X:::::::::X     \n       SSSSSS::::S        I::::I           X:::::X:::::X    \n            S:::::S       I::::I          X:::::X X:::::X   \n            S:::::S       I::::I       XXX:::::X   X:::::XXX\nSSSSSSS     S:::::S     II::::::II     X::::::X     X::::::X\nS::::::SSSSSS:::::S     I::::::::I     X:::::X       X:::::X\nS:::::::::::::::SS      I::::::::I     X:::::X       X:::::X\n SSSSSSSSSSSSSSS        IIIIIIIIII     XXXXXXX       XXXXXXX";
        return six;
    }
    
    public String drawSeven() {
        String seven = "   SSSSSSSSSSSSSSS      EEEEEEEEEEEEEEEEEEEEEE     VVVVVVVV           VVVVVVVV     EEEEEEEEEEEEEEEEEEEEEE     NNNNNNNN        NNNNNNNN\n SS:::::::::::::::S     E::::::::::::::::::::E     V::::::V           V::::::V     E::::::::::::::::::::E     N:::::::N       N::::::N\nS:::::SSSSSS::::::S     E::::::::::::::::::::E     V::::::V           V::::::V     E::::::::::::::::::::E     N::::::::N      N::::::N\nS:::::S     SSSSSSS     EE::::::EEEEEEEEE::::E     V::::::V           V::::::V     EE::::::EEEEEEEEE::::E     N:::::::::N     N::::::N\nS:::::S                   E:::::E       EEEEEE      V:::::V           V:::::V        E:::::E       EEEEEE     N::::::::::N    N::::::N\nS:::::S                   E:::::E                    V:::::V         V:::::V         E:::::E                  N:::::::::::N   N::::::N\n S::::SSSS                E::::::EEEEEEEEEE           V:::::V       V:::::V          E::::::EEEEEEEEEE        N:::::::N::::N  N::::::N\n  SS::::::SSSSS           E:::::::::::::::E            V:::::V     V:::::V           E:::::::::::::::E        N::::::N N::::N N::::::N\n    SSS::::::::SS         E:::::::::::::::E             V:::::V   V:::::V            E:::::::::::::::E        N::::::N  N::::N:::::::N\n       SSSSSS::::S        E::::::EEEEEEEEEE              V:::::V V:::::V             E::::::EEEEEEEEEE        N::::::N   N:::::::::::N\n            S:::::S       E:::::E                         V:::::V:::::V              E:::::E                  N::::::N    N::::::::::N\n            S:::::S       E:::::E       EEEEEE             V:::::::::V               E:::::E       EEEEEE     N::::::N     N:::::::::N\nSSSSSSS     S:::::S     EE::::::EEEEEEEE:::::E              V:::::::V              EE::::::EEEEEEEE:::::E     N::::::N      N::::::::N\nS::::::SSSSSS:::::S     E::::::::::::::::::::E               V:::::V               E::::::::::::::::::::E     N::::::N       N:::::::N\nS:::::::::::::::SS      E::::::::::::::::::::E                V:::V                E::::::::::::::::::::E     N::::::N        N::::::N\n SSSSSSSSSSSSSSS        EEEEEEEEEEEEEEEEEEEEEE                 VVV                 EEEEEEEEEEEEEEEEEEEEEE     NNNNNNNN         NNNNNNN\n";
        return seven;
    }
    
    public String drawEight() {
        String eight = "EEEEEEEEEEEEEEEEEEEEEE     IIIIIIIIII             GGGGGGGGGGGGG     HHHHHHHHH     HHHHHHHHH     TTTTTTTTTTTTTTTTTTTTTTT\nE::::::::::::::::::::E     I::::::::I          GGG::::::::::::G     H:::::::H     H:::::::H     T:::::::::::::::::::::T\nE::::::::::::::::::::E     I::::::::I        GG:::::::::::::::G     H:::::::H     H:::::::H     T:::::::::::::::::::::T\nEE::::::EEEEEEEEE::::E     II::::::II       G:::::GGGGGGGG::::G     HH::::::H     H::::::HH     T:::::TT:::::::TT:::::T\n  E:::::E       EEEEEE       I::::I        G:::::G       GGGGGG       H:::::H     H:::::H       TTTTTT  T:::::T  TTTTTT\n  E:::::E                    I::::I       G:::::G                     H:::::H     H:::::H               T:::::T        \n  E::::::EEEEEEEEEE          I::::I       G:::::G                     H::::::HHHHH::::::H               T:::::T        \n  E:::::::::::::::E          I::::I       G:::::G    GGGGGGGGGG       H:::::::::::::::::H               T:::::T        \n  E:::::::::::::::E          I::::I       G:::::G    G::::::::G       H:::::::::::::::::H               T:::::T        \n  E::::::EEEEEEEEEE          I::::I       G:::::G    GGGGG::::G       H::::::HHHHH::::::H               T:::::T        \n  E:::::E                    I::::I       G:::::G        G::::G       H:::::H     H:::::H               T:::::T        \n  E:::::E       EEEEEE       I::::I        G:::::G       G::::G       H:::::H     H:::::H               T:::::T        \nEE::::::EEEEEEEE:::::E     II::::::II       G:::::GGGGGGGG::::G     HH::::::H     H::::::HH           TT:::::::TT      \nE::::::::::::::::::::E     I::::::::I        GG:::::::::::::::G     H:::::::H     H:::::::H           T:::::::::T      \nE::::::::::::::::::::E     I::::::::I          GGG::::::GGG:::G     H:::::::H     H:::::::H           T:::::::::T      \nEEEEEEEEEEEEEEEEEEEEEE     IIIIIIIIII             GGGGGG   GGGG     HHHHHHHHH     HHHHHHHHH           TTTTTTTTTTT      ";
        return eight;
    }    

    public String drawNine() {
        String nine = "NNNNNNNN        NNNNNNNN     IIIIIIIIII     NNNNNNNN        NNNNNNNN     EEEEEEEEEEEEEEEEEEEEEE\nN:::::::N       N::::::N     I::::::::I     N:::::::N       N::::::N     E::::::::::::::::::::E\nN::::::::N      N::::::N     I::::::::I     N::::::::N      N::::::N     E::::::::::::::::::::E\nN:::::::::N     N::::::N     II::::::II     N:::::::::N     N::::::N     EE::::::EEEEEEEEE::::E\nN::::::::::N    N::::::N       I::::I       N::::::::::N    N::::::N       E:::::E       EEEEEE\nN:::::::::::N   N::::::N       I::::I       N:::::::::::N   N::::::N       E:::::E             \nN:::::::N::::N  N::::::N       I::::I       N:::::::N::::N  N::::::N       E::::::EEEEEEEEEE   \nN::::::N N::::N N::::::N       I::::I       N::::::N N::::N N::::::N       E:::::::::::::::E   \nN::::::N  N::::N:::::::N       I::::I       N::::::N  N::::N:::::::N       E:::::::::::::::E   \nN::::::N   N:::::::::::N       I::::I       N::::::N   N:::::::::::N       E::::::EEEEEEEEEE   \nN::::::N    N::::::::::N       I::::I       N::::::N    N::::::::::N       E:::::E             \nN::::::N     N:::::::::N       I::::I       N::::::N     N:::::::::N       E:::::E       EEEEEE\nN::::::N      N::::::::N     II::::::II     N::::::N      N::::::::N     EE::::::EEEEEEEE:::::E\nN::::::N       N:::::::N     I::::::::I     N::::::N       N:::::::N     E::::::::::::::::::::E\nN::::::N        N::::::N     I::::::::I     N::::::N        N::::::N     E::::::::::::::::::::E\nNNNNNNNN         NNNNNNN     IIIIIIIIII     NNNNNNNN         NNNNNNN     EEEEEEEEEEEEEEEEEEEEEE\n";
        return nine;
    }
    
    
    public void onFrame(Controller controller) {
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
       
	        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nn\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

            //Get hands
            for(Hand hand : frame.hands()) {
                // Get the hand's normal vector and direction
                Vector normal = hand.palmNormal();
                Vector direction = hand.direction();
                
                //System.out.println("Extended fingers: " + handToLetter(hand));
                System.out.println(determineNumber(hand));           
            }
    
            if (!frame.hands().isEmpty()) {
                System.out.println();
            }
    }
}


