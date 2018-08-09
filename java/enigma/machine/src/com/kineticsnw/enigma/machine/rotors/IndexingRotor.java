/*
 * Copyright (c) 2018- Kinetics Northwest LLC.
 * See the LICENSE.TXT file for the specific language governing permissions and limitations.
 */

package com.kineticsnw.enigma.machine.rotors;

import com.kineticsnw.enigma.machine.engine.EngineCONST;
import com.kineticsnw.enigma.machine.engine.RotorSetUp;
/**
 * Class responsibilities & contract
 *
 * @author John Hake
 * @since 2018
 */
public class IndexingRotor extends MachineRotor {

// Properties

// Methods

  public IndexingRotor() { }

  public IndexingRotor( RotorSetUp rotorConfig ) {
    super(rotorConfig);
  }

  @Override
  public void resetRotor() {
    super.resetRotor();
  }

  @Override
  public Character getCurrentRotorPos() {
    return super.getCurrentRotorPos();
  }

  @Override
  public void setCurrentRotorPos( Character newRotorPos ) {
    super.setCurrentRotorPos( newRotorPos );
  }

  @Override
  public boolean atIndexPos() {
    return super.atIndexPos();
  }

  @Override
  public Character forwardTransform( Character rightInput ) {
    return super.forwardTransform( rightInput );
  }

  @Override
  public Character inverseTransform( Character leftInput ) {
    return super.inverseTransform( leftInput );
  }

  @Override
  protected void setMyType( EngineCONST.RotorType myType ) {
    super.setMyType( myType );
  }

  @Override
  protected void setIndexLetter( Character indexLetter ) {
    super.setIndexLetter( indexLetter );
  }

  @Override
  protected void setStartRotorPos( Character startRotorPos ) {
    super.setStartRotorPos( startRotorPos );
  }

  @Override
  public Character getStartRotorPos() {
    return super.getStartRotorPos();
  }

  @Override
  protected void setRingPos( Character ringPos ) {
    super.setRingPos( ringPos );
  }

  @Override
  protected void setWiringMap( RotorUtils.WiringTable wiringMap ) {
    super.setWiringMap( wiringMap );
  }
}
