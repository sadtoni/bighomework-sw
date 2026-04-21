<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <style>
                    table { border-collapse: collapse; width: 100%; }
                    th, td { border: 1px solid black; padding: 8px; text-align: left; }
                    .match { background-color: yellow; }
                    .no-match { background-color: #90EE90; } /* Light Green */
                </style>
            </head>
            <body>
                <h2>Recipe Platform</h2>
                <h3>User: <xsl:value-of select="platform/user/name"/> <xsl:value-of select="platform/user/surname"/></h3>
                <p>Skill Level: <xsl:value-of select="platform/user/skillLevel"/></p>

                <table>
                    <tr style="background-color: #f2f2f2;">
                        <th>Title</th>
                        <th>Cuisines</th>
                        <th>Difficulty</th>
                    </tr>

                    <xsl:variable name="userSkill" select="platform/user/skillLevel" />

                    <xsl:for-each select="platform/recipes/recipe">
                        <tr>
                            <xsl:attribute name="class">
                                <xsl:choose>
                                    <xsl:when test="difficulty = $userSkill">match</xsl:when>
                                    <xsl:otherwise>no-match</xsl:otherwise>
                                </xsl:choose>
                            </xsl:attribute>

                            <td><xsl:value-of select="title"/></td>
                            <td>
                                <xsl:for-each select="cuisine">
                                    <xsl:value-of select="."/><xsl:if test="position() != last()">, </xsl:if>
                                </xsl:for-each>
                            </td>
                            <td><xsl:value-of select="difficulty"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>