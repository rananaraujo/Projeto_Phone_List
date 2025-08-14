using Microsoft.EntityFrameworkCore;
using AgendaAPI.Models;

namespace AgendaAPI.Data
{
    public class AgendaContext : DbContext
    {
        public AgendaContext(DbContextOptions<AgendaContext> options) : base(options)
        {
        }

        public DbSet<Contato> Contatos { get; set; }
        public DbSet<Usuario> Usuarios { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
        }
    }
}