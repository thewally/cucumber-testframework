<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:example="http://ws.cdyne.com/">
    <SOAP-ENV:Header/>
    <SOAP-ENV:Body>
        <example:VerifyEmail>
            <example:email>${email}</example:email>
            <example:LicenseKey>${licensekey}</example:LicenseKey>
        </example:VerifyEmail>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>