package utils;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by szpt_user045 on 02.08.2019.
 */
public final class EmailValidator {
    public static boolean validate(String address){
        int pos = address.indexOf( '@' );
        if ( pos == -1 ) return false;
        String domain = address.substring( ++pos );

        ArrayList mxList;
        try {
            mxList = getMX( domain );
        }
        catch (NamingException ex) {
            return false;
        }
        if ( mxList.size() == 0 ) return false;
        for (Object aMxList : mxList) {
            boolean valid = false;
            try {
                int res;
                Socket skt = new Socket((String) aMxList, 25);
                BufferedReader rdr = new BufferedReader
                        (new InputStreamReader(skt.getInputStream()));
                BufferedWriter wtr = new BufferedWriter
                        (new OutputStreamWriter(skt.getOutputStream()));
                res = hear(rdr);
                if (res != 220) throw new Exception("Invalid header");
                say(wtr, "EHLO orbaker.com");
                res = hear(rdr);
                if (res != 250) throw new Exception("Not ESMTP");
                // validate the sender address
                say(wtr, "MAIL FROM: <tim@orbaker.com>");
                res = hear(rdr);
                if (res != 250) throw new Exception("Sender rejected");
                say(wtr, "RCPT TO: <" + address + ">");
                res = hear(rdr);
                // be polite
                say(wtr, "RSET");
                hear(rdr);
                say(wtr, "QUIT");
                hear(rdr);
                if (res != 250)
                    throw new Exception("Address is not valid!");
                valid = true;
                rdr.close();
                wtr.close();
                skt.close();
            } catch (Exception ex) {
                // Do nothing but try next host
            } finally {
                if (valid) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void say( BufferedWriter wr, String text )
            throws IOException {
        wr.write( text + "\r\n" );
        wr.flush();
    }

    private static ArrayList getMX(String domain) throws NamingException {
        Hashtable<String, String> env = new Hashtable<>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        DirContext dirContext = new InitialDirContext( env );
        Attributes attrs = dirContext.getAttributes( domain, new String[] { "MX" });
        Attribute attr = attrs.get( "MX" );
        if (( attr == null ) || ( attr.size() == 0 )) {
            attrs = dirContext.getAttributes( domain, new String[] { "A" });
            attr = attrs.get( "A" );
            if( attr == null ) {
                throw new NamingException("No match for name '" + domain + "'");
            }
        }
        ArrayList<String> res = new ArrayList<>();
        NamingEnumeration en = attr.getAll();
        while ( en.hasMore() ) {
            String x = (String) en.next();
            String f[] = x.split( " " );
            if ( f[1].endsWith( "." ) )
                f[1] = f[1].substring( 0, (f[1].length() - 1));
            res.add( f[1] );
        }
        return res;
    }
    private static int hear( BufferedReader in ) throws IOException {
        String line;
        int res = 0;
        while ( (line = in.readLine()) != null ) {
            String pfx = line.substring( 0, 3 );
            try {
                res = Integer.parseInt( pfx );
            }
            catch (Exception ex) {
                res = -1;
            }
            if ( line.charAt( 3 ) != '-' ) break;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(validate("stasvasil@gmail.com"));
        System.out.println(validate("stasvasilin@gmail.com"));
    }
}
