/*
 * Copyright 2017-2020 The BlockyArena Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.huskycraft.blockyarena.arenas;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.reflect.TypeToken;
import net.huskycraft.blockyarena.BlockyArena;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import java.util.UUID;

public class SpawnSerializer implements TypeSerializer<Spawn> {

    public static BlockyArena plugin;

    public SpawnSerializer(BlockyArena plugin) {
        this.plugin = plugin;
    }

    @Override
    public Spawn deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        UUID extentUUID = value.getNode("extent").getValue(TypeToken.of(UUID.class));
        if (!Sponge.getServer().getWorld(extentUUID).isPresent()) {
            plugin.getLogger().warn("Cannot find extent with UUID " + extentUUID.toString());
        }
        Extent extent = Sponge.getServer().getWorld(extentUUID).get();
        Location<World> spawnLocation = new Location(
                 extent,
                new Vector3d(value.getNode("location").getValue(TypeToken.of(Vector3d.class))));
        Vector3d spawnRotation = value.getNode("rotation").getValue(TypeToken.of(Vector3d.class));
        return new Spawn(spawnLocation, spawnRotation);
    }

    @Override
    public void serialize(TypeToken<?> type, Spawn obj, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("extent").setValue(TypeToken.of(UUID.class), obj.getSpawnLocation().getExtent().getUniqueId());
        value.getNode("location").setValue(TypeToken.of(Vector3d.class), obj.getSpawnLocation().getPosition());
        value.getNode("rotation").setValue(TypeToken.of(Vector3d.class), obj.getSpawnRotation());
    }
}