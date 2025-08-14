using AgendaAPI.Models;
using AgendaAPI.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using AgendaAPI.Data;
using BCrypt.Net;

namespace AgendaAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly AgendaContext _context;
        private readonly AuthService _authService;

        public AuthController(AgendaContext context, AuthService authService)
        {
            _context = context;
            _authService = authService;
        }

        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody] Usuario usuario)
        {
            if (await _context.Usuarios.AnyAsync(u => u.Email == usuario.Email))
                return BadRequest("Email já cadastrado");

            usuario.Senha = BCrypt.Net.BCrypt.HashPassword(usuario.Senha);

            _context.Usuarios.Add(usuario);
            await _context.SaveChangesAsync();

            return Ok(new { usuario.Email });
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] LoginRequest request)
        {
            var usuario = await _context.Usuarios.FirstOrDefaultAsync(u => u.Email == request.Email);

            if (usuario == null || !BCrypt.Net.BCrypt.Verify(request.Senha, usuario.Senha))
                return Unauthorized("Email ou senha inválidos");

            var token = _authService.GenerateToken(usuario);

            return Ok(new { Token = token });
        }
    }

    public class LoginRequest
    {
        public string Email { get; set; }
        public string Senha { get; set; }
    }
}