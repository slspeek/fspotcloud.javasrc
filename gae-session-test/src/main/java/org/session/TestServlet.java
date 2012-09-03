/*
 * Copyright 2010-2012 Steven L. Speek.
 * This program is free software; you can redistribute it
                and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free
                Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is
                distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
                warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public
                License for more details.
 * You should have received a copy of the GNU General Public License
 * along
                with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330,
                Boston, MA 02111-1307, USA.
 *
 */

package org.session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;


@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {
    public static final String DIRECT = "direct";
    public static final String STRUCT = "struct";

    private Struct getStruct(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Struct previousValue = (Struct) session.getAttribute(STRUCT);

        if (previousValue == null) {
            previousValue = new Struct();
            session.setAttribute(STRUCT, previousValue);
        }

        return previousValue;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");

        if (name != null) {
            appendToSessionDirect(name, request);
        }

        String second = request.getParameter("second");

        if (second != null) {
            appendToStruct(second, request);
        }

        OutputStream out = response.getOutputStream();
        PrintWriter p = new PrintWriter(out);
        p.write(outputHTML(request));
        p.close();
        out.close();
    }

    private void appendToStruct(String second, HttpServletRequest request) {
        Struct s = getStruct(request);
        s.setBuffer(second);
    }

    private void appendToSessionDirect(String name, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String previousValue = (String) session.getAttribute(DIRECT);

        if (previousValue != null) {
            previousValue += " " + name;
        } else {
            previousValue = name;
        }

        session.setAttribute(DIRECT, previousValue);
    }

    private String outputHTML(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String result = "<html><h1>Session Test Servlet</h1><div>";
        result += "DIRECT " + session.getAttribute(DIRECT);

        Struct s = getStruct(request);
        result += "<br>VIA STRUCT " + s.getBuffer();
        result += "</div></html>";

        return result;
    }

    public class Struct implements Serializable {
        String buffer;

        public String getBuffer() {
            return buffer;
        }

        public void setBuffer(String buffer) {
            this.buffer = buffer;
        }
    }
}
