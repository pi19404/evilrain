/*******************************************************************************
 * Copyright 2013 See AUTHORS file.
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE V3
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.gnu.org/licenses/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.alex.rain.models;

import com.alex.rain.RainGame;
import com.alex.rain.managers.TextureManager;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;

public class Cloud extends KinematicActor {
    Animation animation;
    TextureRegion leftTextureRegion;
    TextureRegion rightTextureRegion;
    TextureRegion stayTextureRegion;
    TextureRegion textureRegion;

    final int FRAME_ROWS = 2;
    final int FRAME_COLS = 3;

    int direction;

    public Cloud() {
        sprite = TextureManager.getInstance().getSpriteFromDefaultAtlas("cloud");

        TextureRegion[][] tmp = sprite.split((int)sprite.getWidth() / FRAME_COLS, (int)sprite.getHeight() / FRAME_ROWS);

        leftTextureRegion = tmp[0][1];
        rightTextureRegion = tmp[0][2];
        stayTextureRegion = tmp[0][0];
        TextureRegion[] animTextureRegion = new TextureRegion[3];
        for(int i = 1; i < FRAME_ROWS; i++) {
            for(int j = 0; j < FRAME_COLS; j++) {
                animTextureRegion[j] = tmp[i][j];
            }
        }
        animation = new Animation(0.25f, animTextureRegion);

        offset.set(-100, -50);
        type = SimpleActor.TYPE.CLOUD;
        setBodyBox(200, 100);
    }

    @Override
    public void createPhysicsActor(World physicsWorld) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(getPhysicsWidth() / 2, getPhysicsHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1;
        fixtureDef.friction = 10.4f;
        fixtureDef.filter.categoryBits = CATEGORY_CLOUD;
        fixtureDef.filter.maskBits = MASK_NONE;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        body = physicsWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.resetMassData();

        polygonShape.dispose();

        setWidth(200);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if(direction < 0) {
            batch.draw(animation.getKeyFrame(RainGame.getTime(), true), pos.x + offset.x, pos.y + offset.y);
        } else if(direction == 1) {
            batch.draw(leftTextureRegion, pos.x + offset.x, pos.y + offset.y);
        } else if(direction == 2) {
            batch.draw(rightTextureRegion, pos.x + offset.x, pos.y + offset.y);
        } else {
            batch.draw(stayTextureRegion, pos.x + offset.x, pos.y + offset.y);
        }
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
