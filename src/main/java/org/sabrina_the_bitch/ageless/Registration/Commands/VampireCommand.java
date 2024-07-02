package org.sabrina_the_bitch.ageless.Registration.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.sabrina_the_bitch.ageless.MixinInterfaces.PlayerVampirism;

import java.util.Collection;

import static net.minecraft.server.command.CommandManager.*;

public final class VampireCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        dispatcher.register(literal("vampire")
            .then(literal("check")
                .executes(context -> checkVampire(context.getSource().getPlayer(), context))
                .then(argument("targets", EntityArgumentType.players())
                    .executes(context -> checkVampires(EntityArgumentType.getPlayers(context, "targets"), context))
                )
            )
            .then(literal("set")
                .then(argument("value", BoolArgumentType.bool())
                    .executes(context -> setVampire(context.getSource().getPlayer(), BoolArgumentType.getBool(context, "value"), context))
                )
                .then(argument("targets", EntityArgumentType.players())
                    .then(argument("value", BoolArgumentType.bool())
                        .executes(context -> setVampires(EntityArgumentType.getPlayers(context, "targets"), BoolArgumentType.getBool(context, "value"), context))
                    )
                )
            )
        );
    }

    public static int checkVampire(ServerPlayerEntity target, CommandContext<ServerCommandSource> context) {
        String text = target.getName().getString() + ": Vampirism: " + ((PlayerVampirism) target).isVampire().get();
        context.getSource().sendFeedback(() -> Text.of(text), false);
        return 1;
    }

    public static int checkVampires(Collection<ServerPlayerEntity> targets, CommandContext<ServerCommandSource> context) {
        for (ServerPlayerEntity e : targets) {
            checkVampire(e, context);
        }
        return 1;
    }

    public static int setVampire(ServerPlayerEntity target, boolean value, CommandContext<ServerCommandSource> context) {
        ((PlayerVampirism) target).setVampire(value);
        context.getSource().sendFeedback(() -> Text.of("Set " + target.getName().getString() + " Vampirism: " + value), true);
        return 0;
    }

    public static int setVampires(Collection<ServerPlayerEntity> targets, boolean value, CommandContext<ServerCommandSource> context) {
        for (ServerPlayerEntity e : targets) {
            setVampire(e, value, context);
        }
        return 1;
    }
}
