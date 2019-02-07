package grazzinisoftwares.truthordarecomplete;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import grazzinisoftwares.truthordarecomplete.Challenge;
import grazzinisoftwares.truthordarecomplete.Gender;
import grazzinisoftwares.truthordarecomplete.Helper;
import grazzinisoftwares.truthordarecomplete.Player;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Helper.LoadDisplayables(appContext);
        ArrayList<Challenge> brokenChallenges = new ArrayList<>();
        for(Challenge c : Helper.allChallenges){
            int i = 0;
            ArrayList<Player> p = new ArrayList<>();
            for(Gender g : c.targets){
                p.add(new Player(i + "", g));
                i++;
            }
            String s = Helper.buildDisplayableText(c, p);
            if(s.contains("{")){
                brokenChallenges.add(c);
            }
        }
        assertEquals(0, brokenChallenges.size());
    }
}
