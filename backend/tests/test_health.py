"""Test health check and application bootstrap."""

import unittest


class TestHealthEndpoint(unittest.TestCase):

    def test_health_response_structure(self):
        """Validates the health check schema contract."""
        from backend.main import health_check
        response = health_check()
        self.assertEqual(response["status"], "healthy")
        self.assertEqual(response["service"], "emergency-fleet-routing-backend")
        self.assertIn("timestamp", response)
        self.assertIn("version", response)


if __name__ == "__main__":
    unittest.main()
