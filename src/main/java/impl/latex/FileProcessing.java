/*
Copyright 2016 Laurent Claessens
contact : moky.math@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
//*/

package actors.impl.latex;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import java.util.Map;
import java.util.HashMap;

class FileProcessing
/*
    This class has a 'run' method which is intended to read a LaTeX file and replace the "\input" lines by the content of the file (recursive)
//*/
{
    private String filename;
    private String content;

    FileProcessing(String filename) 
    {
        this.filename=filename;
        this.content="This should never be seen.";
    }
    public String run() 
    {
        String line;
        try (
            InputStream fis = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            )
        {
            int n=0;
            String content="";
            Map<Integer,String> line_to_filename = new HashMap<Integer,String>();
            while ((line = br.readLine()) != null) 
            {
                content=content+line+"\n";
                int input_index = line.indexOf("\\input{");
                if (input_index>=0)
                {
                    int end_index=line.indexOf("}",input_index);
                    String filename=line.substring(input_index+7,end_index);
                    line_to_filename.put(n,filename);
                }
                n=n+1;
            }
            // from http://stackoverflow.com/questions/46898/how-to-efficiently-iterate-over-each-entry-in-a-map
            for (Map.Entry<Integer, String> entry : line_to_filename.entrySet())
            {
                System.out.println(entry.getKey() + "/" + entry.getValue());
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found : "+filename);
            content="\\huge FILE NOT FOUND : "+filename;
            return content;
        }
        catch (IOException e)
        {
            System.out.println("IO Error on file "+filename);
            content="\\huge IO ERROR ON FILE : "+filename;
            return content;
        }
        return "The file "+filename+" has been read";
    }
}
