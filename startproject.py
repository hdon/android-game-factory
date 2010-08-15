from sys import argv
from os import listdir, makedirs, system
import errno

if len(argv) != 3:
  print 'usage: %s <project name> <project path>' % argv[0]
  print '       creates a new project based on this skeleton,'
  print '       using the name you supply at the path you supply.'
  print '       <project name> should be fully qualified, ie.'
  print '       com.example.android.supermariobros as it becomes'
  print '       the java package name.'
  sys.exit(1)

def mkdir(path):
  print 'mkdir(%s)' % path
  stack = [path]
  while stack:
    try:
      makedirs(stack[-1])
      stack.pop()
    except OSError as exc:
      if exc.errno == errno.ENOENT: stack.append(stack[-1].rpartition('/')[0])
      elif exc.errno == errno.EEXIST: stack.pop()
      else: raise

project_name = argv[1]
project_path = argv[2]
src_dir = project_path + '/src/' + project_name.replace('.', '/')
simple_name = project_name.rpartition('.')[-1]

substitutions = {
  'package_name':     project_name,
  'simple_name':      simple_name,
  'activity_class':   simple_name.capitalize() + 'Activity',
  'view_class':       simple_name.capitalize() + 'View',
  'renderer_class':   simple_name.capitalize() + 'Renderer'
}

srcfiles = listdir('src')

mkdir(src_dir)

for srcf in srcfiles:
  f = open(src_dir + '/' + srcf.replace('Game', simple_name.capitalize()), 'w')
  code = open('src/' + srcf).read()
  print 'processing', srcf
  f.write(code % substitutions)
  f.close()

mkdir(project_path + '/res/values')
morefiles = [
  'AndroidManifest.xml',
  'build.xml',
  'res/values/strings.xml',
]
for srcf in morefiles:
  f = open(project_path + '/' + srcf, 'w')
  code = open(srcf).read()
  print 'processing', srcf
  f.write(code % substitutions)
  f.close()

mkdir(project_path + '/res/drawable-hdpi')
mkdir(project_path + '/res/drawable-mdpi')
mkdir(project_path + '/res/drawable-ldpi')
copyfiles = [
  'build.properties',
  'local.properties',
  'default.properties',
  'res/drawable-hdpi/icon.png',
  'res/drawable-mdpi/icon.png',
  'res/drawable-ldpi/icon.png',
]

for srcf in copyfiles:
  f = open(project_path + '/' + srcf, 'w')
  code = open(srcf).read()
  print 'processing', srcf
  f.write(code)
  f.close()

print 'done'
