/**
 *  Strength interface, defines how a politicians Strengths should be defined.
 *
 *  @author Anders Bille Wiggers
 *  for Introduction-to-Human-Computer-InteractionI course.
 *  Copyright (c) 2018.
 *
 */

package aacorp.mypolitician.framework;

public interface Strength {

    /**
     * @return Should return the politicians strength in percent
     */
    int getPercent();

    /**
     * @return Should return the politicians strength note as a string
     */
    String getText();
    
}
