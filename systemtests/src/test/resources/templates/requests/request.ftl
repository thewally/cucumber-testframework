<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:example="http://ws.cdyne.com/">
    <SOAP-ENV:Header/>
    <SOAP-ENV:Body>
        <example:VerifyEmail>
            <#if email??>
            <example:email>${email}</example:email>
            </#if>
            <example:LicenseKey>${licensekey!}</example:LicenseKey>
        </example:VerifyEmail>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>