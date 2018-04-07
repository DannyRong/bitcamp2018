/******************************************************************************\
* Copyright (C) 2012-2016 Leap Motion, Inc. All rights reserved.               *
* Leap Motion proprietary and confidential. Not for distribution.              *
* Use subject to the terms of the Leap Motion SDK Agreement available at       *
* https://developer.leapmotion.com/sdk_agreement, or another agreement         *
* between Leap Motion and you, your company or other organization.             *
\******************************************************************************/

import java.io.IOException;
import java.lang.Math;
import com.leapmotion.leap.*;


class Sample {
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
    

    public void onFrame(Controller controller) {
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
       
	        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("Frame id: " + frame.id()
                             + ", timestamp: " + frame.timestamp()
                             + ", hands: " + frame.hands().count()
                             + ", fingers: " + frame.fingers().count());
    
            //Get hands
            for(Hand hand : frame.hands()) {
                String handType = hand.isLeft() ? "Left hand" : "Right hand";
                System.out.println("  " + handType + ", \tid: " + hand.id()
                                 + ", \tpalm position: " + hand.palmPosition());
    
                // Get the hand's normal vector and direction
                Vector normal = hand.palmNormal();
                Vector direction = hand.direction();
    
                // Calculate the hand's pitch, roll, and yaw angles
                System.out.println("  pitch: " + Math.toDegrees(direction.pitch()) + " degrees, "
                                 + "\troll: " + Math.toDegrees(normal.roll()) + " degrees, "
                                 + "\tyaw: " + Math.toDegrees(direction.yaw()) + " degrees");
    
                // Get arm bone
                Arm arm = hand.arm();
                System.out.println("  Arm direction: " + arm.direction()
                                 + ", \twrist position: " + arm.wristPosition()
                                 + ", \telbow position: " + arm.elbowPosition());
    
                // Get fingers
                for (Finger finger : hand.fingers()) {
                    System.out.println("    " + finger.type() + ", id: " + finger.id()
                                     + ", \tlength: " + finger.length()
                                     + "mm, \twidth: " + finger.width() + "mm" + ", \ttoString output: " + finger.toString());
    
                    //Get Bones
                    for(Bone.Type boneType : Bone.Type.values()) {
                        Bone bone = finger.bone(boneType);
                        System.out.println("      " + bone.type()
                                         + "\t bone, start: " + bone.prevJoint()
                                         + "  \t, end: " + bone.nextJoint()
                                         + "  \t, direction: " + bone.direction());
                    }
                }
            }
    
            if (!frame.hands().isEmpty()) {
                System.out.println();
            }
    }
}


