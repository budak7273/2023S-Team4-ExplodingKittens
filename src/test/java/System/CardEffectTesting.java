package system;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class CardEffectTesting {
    @Test
    public void testDefuseBombEffectUse() {
        EffectPattern bombEffectPattern = new DefuseBombEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        EasyMock.replay(gameState);

        Executable executable = () -> bombEffectPattern.useEffect(gameState);
        Assertions.assertDoesNotThrow(executable);

        EasyMock.verify(gameState);
    }

    @Test
    public void testAttackEffectUse() {
        EffectPattern bombEffectPattern = new AttackEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        EasyMock.replay(gameState);

        Executable executable = () -> bombEffectPattern.useEffect(gameState);
        Assertions.assertDoesNotThrow(executable);

        EasyMock.verify(gameState);
    }

    @Test
    public void testDrawFromBottom() {
        EffectPattern drawFromBottomEffect = new DrawFromBottomEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        gameState.drawFromBottom();
        EasyMock.expectLastCall();
        EasyMock.replay(gameState);

        drawFromBottomEffect.useEffect(gameState);

        EasyMock.verify(gameState);
    }

}
