package com.company;
import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class MiniMiniMusicApp implements ControllerEventListener{

    static JFrame f = new JFrame("MY FIRST MUSIC VIDEO");
    static MyDrawPanell ml;

    public void setGUI(){
        ml = new MyDrawPanell();
        f.setContentPane(ml);
        f.setBounds(30,30,100,100);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void play(){
        setGUI();
        try{
            Sequencer player = MidiSystem.getSequencer(); // Sequencer is the musical device on which music is played
            player.open();
            int []eventsIWant = {127};
            player.addControllerEventListener(ml, eventsIWant);
            Sequence seq = new Sequence(Sequence.PPQ, 4); //Sequence is like a CD rom with a track in it.
            Track t = seq.createTrack(); //A track is a song in the sequence

            int r = 0;
            for(int i =0; i<60 ; i=i+4){

                r = (int)((Math.random() *50) + 1);
                t.add(makeEvent(144,1,r,100,i));
                //176 is the controller event. It will do nothing but trigger on each note on operation(144)
                t.add(makeEvent(176,1,127,0,i));
                t.add(makeEvent(128,1,r,100,i+2));
            }

            player.setSequence(seq);
            player.setTempoInBPM(120);
            player.start();


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void controlChange(ShortMessage event) {
        System.out.println("xl");
    }

    public static MidiEvent makeEvent(int comm, int chan, int one, int two, int tick){
        MidiEvent ev = null;
        try{
            //Short message is made for the midievent. Event forms a code that the sequencer listens.
            //command says note on or off. Channel says what kind of instrument. 44 is the note to play, 100 is velocity
            //128 is note off
            //Midievent says what to do and when to do it. Therefore, timing is important. It says to play message b
            //at beat 16. The track keeps hold of all the events which together forms the music.

            ShortMessage a = new ShortMessage();
            a.setMessage(comm,chan,one,two);
            ev = new MidiEvent(a,tick);

        }
        catch (Exception e){

        }
        return ev;

    }

    //inner class can access all the variables of main class.
    class MyDrawPanell extends JPanel implements ControllerEventListener {
        boolean msg = false;

        @Override
        public void controlChange(ShortMessage event) {
            msg =true;
            paintComponents(ml.getGraphics());
        }


        public void paintComponents(Graphics g) {
            if(msg){
                Graphics2D g2 = (Graphics2D) g;
                int r = (int)(Math.random() *250);
                int gb = (int)(Math.random() *250);
                int b = (int)(Math.random() *250);

                g.setColor(new Color(r,gb,b));

                int ht = (int)((Math.random() *120) + 10);
                int wid = (int)((Math.random() *120) + 10);
                int x = (int)((Math.random() *40) + 10);
                int y = (int)((Math.random() *40) + 10);

                g.fillRect(x,y,ht,wid);
                msg= false;
            }
        }
    }
}
