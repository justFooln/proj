/*
 * Copyright (c) 2018- Kinetics Northwest LLC.
 * See the LICENSE.TXT file for the specific language governing permissions and limitations.
 */

package com.kineticsnw.enigma.machine.rotors;

import com.kineticsnw.enigma.machine.engine.RotorSetUp;


/**
 * Class responsibilities & contract
 *
 * @author John Hake
 * @since 2018
 */
public class Reflector extends MachineRotor {

  public Reflector() { }

  public Reflector(RotorSetUp rotorConfig ) {
    super(rotorConfig);
  }

  @Override
  public void resetRotor() {
    super.resetRotor();
  }

  @Override
  public Character getCurrentRotorPos() {
    return super.getStartRotorPos();
  }

  @Override
  public boolean atIndexPos() {
    return false;
  }

  @Override
  public Character forwardTransform( Character rightInput ) {
    return super.forwardTransform( rightInput );
  }

  @Override
  public Character inverseTransform( Character leftInput ) {
    return super.inverseTransform( leftInput );
  }
}
