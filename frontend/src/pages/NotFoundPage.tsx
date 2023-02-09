import "./NotFoundPage.css"
import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";

export default function NotFoundPage() {

    return (
        <div className={"NotFound"}>
            <Navbar variant={"dark"} bg={"primary"} className={"MenuBar"}>
                <Container fluid>
                    <Navbar.Brand>Page not found!</Navbar.Brand>
                </Container>
            </Navbar>
            <div className={"NotFoundMessage"}>
                <h6>Looks like the page you were looking for does not exist. Perhaps a typo?</h6>
                <h6>Either way, if you would like to go back to your dashboard <a href={"/"}>simply click here!</a></h6>
            </div>
        </div>
    )
}