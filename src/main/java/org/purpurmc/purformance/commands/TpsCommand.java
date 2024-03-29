package org.purpurmc.purformance.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.command.builder.Command;

public class TpsCommand extends Command {
    private final Component prefix = Component.text()
            .append(Component.text("(").color(NamedTextColor.DARK_GRAY))
            .append(Component.text("⚡").color(NamedTextColor.LIGHT_PURPLE).decorate(TextDecoration.BOLD))
            .append(Component.text(")").color(NamedTextColor.DARK_GRAY))
            .build();
    private final Component times = Component.text("(5ns, 5µs, 5ms, 5s)").color(NamedTextColor.GRAY).decorate(TextDecoration.ITALIC);
    private final Component infinity = Component.text("∞");
    private final Component zero = Component.text("0");
    private final Component comma = Component.text(", ").color(NamedTextColor.DARK_GRAY);

    private final Component tpsMessage = Component.text()
            .appendNewline()
            .append(prefix).appendSpace()
            .append(Component.text("TPS").color(NamedTextColor.AQUA).decorate(TextDecoration.BOLD))
            .appendSpace()
            .append(times)
            .appendNewline()

            .append(prefix).appendSpace()
            .append(rainbow(infinity))
            .appendNewline()

            .appendNewline()
            .append(prefix).appendSpace()
            .append(Component.text("MSPT").color(NamedTextColor.AQUA).decorate(TextDecoration.BOLD))
            .appendSpace()
            .append(times)
            .appendNewline()

            .append(prefix).appendSpace()
            .append(rainbow(zero))
            .build();

    private Component rainbow(Component component) {
        return Component.text()
                .append(component.color(NamedTextColor.RED)).append(comma)
                .append(component.color(NamedTextColor.GREEN)).append(comma)
                .append(component.color(NamedTextColor.BLUE)).append(comma)
                .append(component.color(NamedTextColor.LIGHT_PURPLE))
                .build();
    }

    public TpsCommand() {
        super("tps", "mspt");

        setDefaultExecutor((sender, context) -> sender.sendMessage(tpsMessage));
    }
}
