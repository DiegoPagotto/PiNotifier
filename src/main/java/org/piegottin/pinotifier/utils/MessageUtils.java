package org.piegottin.pinotifier.utils;

public class MessageUtils {

    private static String prefix = "§6§l[§rPINotifier§6§l] §9§l» §r";

    public static String addUserViaChat = prefix + "§aDigite no chat o §rnome §ado amigo que deseja adicionar";
    public static String removeFriendViaInv = prefix + "§cO usuário §r{user} §cfoi removido da sua lista de amigos!";
    public static String itsNotAFriend = prefix + "§cO usuário §r{user} §cnão é seu amigo ainda!";
    public static String noFriends = prefix + "§cVocê não tem amigos adicionados ainda!";
    public static String onlyUser = prefix + "§cEste comando só pode ser executado por um jogador.";
    public static String noPermission = prefix + "§cVocê não tem permissão para executar este comando.";
    public static String definedPhone = prefix + "§aSeu telefone foi definido para §r{phone}§a.";
    public static String listFriends = prefix + "§aSua lista de amigos é:";
    public static String wrongUsage = prefix + "§cUso: §r";
    public static String wrongUsername = prefix + "§cO nome de usuário §r{username} §cnão é válido.";
}
